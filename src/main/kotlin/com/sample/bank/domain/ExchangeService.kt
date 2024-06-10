package com.sample.bank.domain

import com.sample.bank.domain.ports.CurrencyAccountsRepository
import com.sample.bank.domain.ports.ExchangeRateProvider
import java.math.BigDecimal
import java.util.Currency

class ExchangeService(
    private val currencyAccountsRepository: CurrencyAccountsRepository,
    private val exchangeRates: ExchangeRateProvider
) {

    fun doExchange(command: DoExchangeCommand) {
        //todo: verify user is registered
        val currencyAccounts = currencyAccountsRepository.findByAccountOwnerId(command.accountOwnerId)
        val event = currencyAccounts.exchange(command, exchangeRates.getExchangeRate())
    }
}

data class DoExchangeCommand(
    val accountOwnerId: AccountOwnerId,
    val amount: BigDecimal,
    val sourceCurrency: Currency,
    val destinationCurrency: Currency
)