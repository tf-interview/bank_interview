package com.sample.bank.domain.generators

import com.sample.bank.domain.exchange.CurrencyAccount
import com.sample.bank.domain.exchange.CurrencyAccountId
import com.sample.bank.domain.exchange.Money
import java.util.*

object CurrencyAccountGenerator {
    fun currencyAccount(
        id: String = UUID.randomUUID().toString(),
        money: Money
    ) = CurrencyAccount(
        id = CurrencyAccountId(id),
        money = money
    )
}