package com.sample.bank.domain.exchange

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.ports.CurrencyAccountsRepository
import com.sample.bank.domain.ports.ExchangeRateProvider
import com.sample.bank.getLoggerForClass
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.Currency

@Service
class ExchangeService(
    private val currencyAccountsRepository: CurrencyAccountsRepository,
    private val exchangeRates: ExchangeRateProvider
) {

    companion object {
        private val log = getLoggerForClass()
    }

    fun doExchange(command: DoExchangeCommand) {
        //todo: verify user is registered
        val currencyAccounts = currencyAccountsRepository.findByAccountOwnerId(command.accountOwnerId)
        val event = currencyAccounts.exchange(command, exchangeRates.getExchangeRate())
        log.info("money exchanged $event")
        currencyAccountsRepository.saveOrUpdate(event.sourceAccount)
        currencyAccountsRepository.saveOrUpdate(event.destinationAccount)
        log.info("updated currency accounts")
    }
}

data class DoExchangeCommand(
    val accountOwnerId: AccountOwnerId,
    val amount: BigDecimal,
    val sourceCurrency: Currency,
    val destinationCurrency: Currency
)