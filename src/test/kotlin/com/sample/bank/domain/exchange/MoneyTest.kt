package com.sample.bank.domain.exchange

import com.sample.bank.domain.TestCurrencies
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class MoneyTest {

    private val positiveAmount = BigDecimal("100.95")
    private val negativeAmount = BigDecimal("-20.15")

    @Test
    fun shouldThrowOnUnknownCurrency() {
        assertThrows<IllegalArgumentException> {
            Money.from(positiveAmount, TestCurrencies.UNKNOWN)
        }
    }

    @Test
    fun shouldThrowOnNegativeAmount() {
        assertThrows<IllegalArgumentException> {
            Money.from(negativeAmount, TestCurrencies.PLN)
        }
    }
}