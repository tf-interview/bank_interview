package com.sample.bank.domain.generators

import com.sample.bank.domain.TestCurrencies
import com.sample.bank.domain.exchange.Money
import java.math.BigDecimal

/* 🤑 */
object MoneyGenerator {
    fun pln(amount: String) = Money.from(BigDecimal(amount), TestCurrencies.PLN)
    fun usd(amount: String) = Money.from(BigDecimal(amount), TestCurrencies.USD)
}