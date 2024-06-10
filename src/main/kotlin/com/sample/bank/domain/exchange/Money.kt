package com.sample.bank.domain.exchange

import com.sample.bank.domain.ports.ExchangeRate
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency

data class Money(val amount: BigDecimal, val currency: Currency) {

    init {
        require(amount.signum() > -1) { "amount should not be a negative number" }
        require(currency in Currencies.acceptableCurrencies) { "currency $currency is not acceptable" }
    }

    fun subtract(other: Money): Money {
        require(this.currency == other.currency) { "currency $currency is different than ${other.currency}" }
        return from(amount = this.amount.subtract(other.amount), currency = this.currency)
    }

    fun add(other: Money): Money {
        require(this.currency == other.currency) { "currency $currency is different than ${other.currency}" }
        return from(amount = this.amount.add(other.amount), currency = this.currency)
    }

    fun exchange(exchangeRate: ExchangeRate): Money {
        require(this.currency == exchangeRate.from) { "currency $currency is different than ${exchangeRate.from}" }
        return from(amount = this.amount.multiply(exchangeRate.rate), currency = exchangeRate.to)
    }

    companion object {
        private val defaultRoundingMode = RoundingMode.HALF_EVEN
        fun from(amount: BigDecimal, currency: Currency) =
            Money(
                amount = BigDecimal(amount.toString()).setScale(2, defaultRoundingMode),
                currency = currency
            )
    }
}

object Currencies {
    val initialCurrency = Currency.getInstance("PLN")
    val acceptableCurrencies: List<Currency> = listOf(
        Currency.getInstance("PLN"),
        Currency.getInstance("USD")
    )
}