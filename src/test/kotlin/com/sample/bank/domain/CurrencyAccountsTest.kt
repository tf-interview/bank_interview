package com.sample.bank.domain

import com.sample.bank.TestCurrencies
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class CurrencyAccountsTest {

    private val accountOwnerId = AccountOwnerId("AccountOwnerId")

    @Test
    fun shouldThrowWhenMultipleAccountsWithSameCurrency() {
        assertThrows<RuntimeException> {
            CurrencyAccounts(
                accountOwnerId,
                accounts = listOf(
                    generateCurrencyAccount("1", "14.99", TestCurrencies.PLN),
                    generateCurrencyAccount("2", "11.22", TestCurrencies.USD),
                    generateCurrencyAccount("3", "29.09", TestCurrencies.USD)
                )
            )
        }
    }
}

private fun generateCurrencyAccount(id: String, amount: String, currency: Currency): CurrencyAccount {
    return CurrencyAccount(CurrencyAccountId(id), money = Money.from(BigDecimal(amount), currency))
}