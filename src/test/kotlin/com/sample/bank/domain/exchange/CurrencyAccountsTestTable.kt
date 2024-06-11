package com.sample.bank.domain.exchange

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.generators.CurrencyAccountGenerator.currencyAccount
import com.sample.bank.domain.generators.MoneyGenerator.pln
import com.sample.bank.domain.generators.MoneyGenerator.usd
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CurrencyAccountsTestTable {

    private val accountOwnerId = AccountOwnerId("AccountOwnerId")

    @Test
    fun shouldThrowWhenMultipleAccountsWithSameCurrency() {
        assertThrows<RuntimeException> {
            CurrencyAccounts(
                accountOwnerId,
                accounts = listOf(
                    currencyAccount("1", pln("14.99")),
                    currencyAccount("2", usd("11.22")),
                    currencyAccount("3", usd("29.09"))
                )
            )
        }
    }
}