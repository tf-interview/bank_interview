package com.sample.bank.domain.ports

import java.math.BigDecimal
import java.util.Currency

interface ExchangeRateProvider {
    fun getExchangeRate(): ExchangeRate
}

data class ExchangeRate(val rate: BigDecimal, val from: Currency, val to: Currency)
