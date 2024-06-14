package com.sample.bank.domain.exchange

import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.ports.AccountOwnersRepository
import com.sample.bank.domain.ports.CurrencyAccountsRepository
import com.sample.bank.domain.ports.ExchangeRateProvider
import com.sample.bank.getLoggerForClass
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class ExchangeService(
    private val accountOwnersRepository: AccountOwnersRepository,
    private val currencyAccountsRepository: CurrencyAccountsRepository,
    private val exchangeRates: ExchangeRateProvider
) {

    companion object {
        private val log = getLoggerForClass()
    }

    @Transactional
    fun doExchange(command: DoExchangeCommand) {
        val accountOwner = accountOwnersRepository.findByPesel(command.pesel) ?: error("no account for exchange")
        val currencyAccounts = currencyAccountsRepository.findByAccountOwnerId(accountOwner.id)
        val event = currencyAccounts.exchange(command, exchangeRates.getExchangeRate())
        log.info("money exchanged $event")
        currencyAccountsRepository.saveOrUpdate(event.sourceAccount, accountOwner.id)
        currencyAccountsRepository.saveOrUpdate(event.destinationAccount, accountOwner.id)
        log.info("updated currency accounts")
    }
}

data class DoExchangeCommand(
    val pesel: Pesel,
    val amount: BigDecimal,
    val sourceCurrency: Currency,
    val destinationCurrency: Currency
)