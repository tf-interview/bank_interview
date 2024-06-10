package com.sample.bank.domain.exchange

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.ports.ExchangeRate
import java.math.BigDecimal
import java.util.*

data class CurrencyAccounts(val accountOwnerId: AccountOwnerId, val accounts: List<CurrencyAccount>) {

    init {
        accounts.groupBy { it.money.currency }
            .forEach { check(it.value.count() <= 1) { "more than one account found for ${it.key}" } }
    }

    fun exchange(command: DoExchangeCommand, exchangeRate: ExchangeRate): MoneyExchangedEvent {
        val sourceAccount = accounts.findByOrCreate(command.sourceCurrency)
        val destinationAccount = accounts.findByOrCreate(command.destinationCurrency)

        val moneyToExchange = Money.from(command.amount, command.sourceCurrency)
        val sourceAccountAfterExchange = sourceAccount.withdraw(moneyToExchange)

        val exchangedMoney = moneyToExchange.exchange(exchangeRate)
        val destinationAccountAfterExchange = destinationAccount.deposit(exchangedMoney)

        return MoneyExchangedEvent(
            sourceAccount = sourceAccountAfterExchange,
            destinationAccount = destinationAccountAfterExchange,
            moneyToExchange = moneyToExchange,
            exchangedMoney = exchangedMoney,
            exchangeRate = exchangeRate
        )
    }
}

private fun List<CurrencyAccount>.findByOrCreate(currency: Currency) =
    this.firstOrNull { it.money.currency == currency } ?: CurrencyAccount.create(currency)

data class MoneyExchangedEvent(
    val sourceAccount: CurrencyAccount,
    val destinationAccount: CurrencyAccount,
    val moneyToExchange: Money,
    val exchangedMoney: Money,
    val exchangeRate: ExchangeRate
)

data class CurrencyAccount(val id: CurrencyAccountId, val money: Money) {

    fun withdraw(other: Money) =
        copy(money = this.money.subtract(other))

    fun deposit(other: Money) =
        copy(money = this.money.add(other))

    companion object {
        fun create(currency: Currency, initialAmount: BigDecimal = BigDecimal.ZERO) =
            CurrencyAccount(
                id = CurrencyAccountId(UUID.randomUUID().toString()),
                money = Money.from(initialAmount, currency)
            )
    }
}

data class CurrencyAccountId(val raw: String)
