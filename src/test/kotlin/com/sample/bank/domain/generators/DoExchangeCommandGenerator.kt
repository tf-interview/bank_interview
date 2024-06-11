package com.sample.bank.domain.generators

import com.sample.bank.domain.TestCurrencies
import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.exchange.DoExchangeCommand
import com.sample.bank.infrastructure.database.model.AccountOwnerTable.pesel
import java.math.BigDecimal
import java.util.*

object DoExchangeCommandGenerator {
    fun doExchangeCommand(
        pesel: Pesel,
        sourceCurrency: Currency = TestCurrencies.PLN,
        destinationCurrency: Currency = TestCurrencies.USD,
        amount: String = "1"
    ) = DoExchangeCommand(
        pesel = pesel,
        sourceCurrency = sourceCurrency,
        destinationCurrency = destinationCurrency,
        amount = BigDecimal(amount),
    )
}