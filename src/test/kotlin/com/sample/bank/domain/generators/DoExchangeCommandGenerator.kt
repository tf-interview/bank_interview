package com.sample.bank.domain.generators

import com.sample.bank.domain.TestCurrencies
import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.exchange.DoExchangeCommand
import java.math.BigDecimal
import java.util.*

object DoExchangeCommandGenerator {
    fun doExchangeCommand(
        accountOwnerId: AccountOwnerId = AccountOwnerId(UUID.randomUUID().toString()),
        sourceCurrency: Currency = TestCurrencies.PLN,
        destinationCurrency: Currency = TestCurrencies.USD,
        amount: String = "1"
    ) = DoExchangeCommand(
        accountOwnerId = accountOwnerId,
        sourceCurrency = sourceCurrency,
        destinationCurrency = destinationCurrency,
        amount = BigDecimal(amount),
    )
}