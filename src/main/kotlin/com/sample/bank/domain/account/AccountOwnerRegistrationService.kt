package com.sample.bank.domain.account

import com.sample.bank.domain.account.verification.AccountRegistrationVerifiers
import com.sample.bank.domain.exchange.Currencies
import com.sample.bank.domain.exchange.CurrencyAccount
import com.sample.bank.domain.ports.AccountOwnersRepository
import com.sample.bank.domain.ports.CurrencyAccountsRepository
import com.sample.bank.getLoggerForClass
import java.math.BigDecimal

class AccountOwnerRegistrationService(
    private val verifiers: AccountRegistrationVerifiers,
    private val accountOwnersRepository: AccountOwnersRepository,
    private val currencyAccountsRepository: CurrencyAccountsRepository
) {

    fun registerNewAccountOwner(command: RegisterAccountOwnerCommand) {
        verifiers.verify(command)
        val createdAccountOwner = AccountOwner.create(command)
        accountOwnersRepository.saveOrUpdate(createdAccountOwner)
        log.info("registered new $createdAccountOwner")

        val currencyAccount = CurrencyAccount.create(
            Currencies.initialCurrency,
            command.initialAmount
        )
        currencyAccountsRepository.saveOrUpdate(currencyAccount)
        log.info("created $currencyAccount for registered user")
    }

    companion object {
        private val log = getLoggerForClass()
    }
}

data class RegisterAccountOwnerCommand(
    val pesel: Pesel,
    val firstName: FirstName,
    val lastName: LastName,
    val initialAmount: BigDecimal
)

class AccountOwnerAlreadyExistsException(val pesel: Pesel) : RuntimeException()