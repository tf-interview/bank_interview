package com.sample.bank.infrastructure.database.repositories

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.exchange.CurrencyAccount
import com.sample.bank.domain.exchange.CurrencyAccountId
import com.sample.bank.domain.exchange.CurrencyAccounts
import com.sample.bank.domain.exchange.Money
import com.sample.bank.domain.ports.CurrencyAccountsRepository
import com.sample.bank.infrastructure.database.model.CurrencyAccountTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Transactional
@Repository
class SQLCurrencyAccountsRepository : CurrencyAccountsRepository {
    override fun findByAccountOwnerId(accountOwnerId: AccountOwnerId): CurrencyAccounts {
        val currencyAccountList = CurrencyAccountTable.select(
            CurrencyAccountTable.id,
            CurrencyAccountTable.amount,
            CurrencyAccountTable.currency
        ).where { CurrencyAccountTable.accountId.eq(UUID.fromString(accountOwnerId.raw)) }
            .map {
                CurrencyAccount(
                    id = CurrencyAccountId(it[CurrencyAccountTable.id].toString()),
                    Money.from(
                        amount = BigDecimal(it[CurrencyAccountTable.amount]),
                        currency = Currency.getInstance(it[CurrencyAccountTable.currency])
                    )
                )
            }
        return CurrencyAccounts(
            accountOwnerId = accountOwnerId,
            accounts = currencyAccountList
        )
    }

    override fun saveOrUpdate(currencyAccount: CurrencyAccount, accountOwnerId: AccountOwnerId) {
        val exists =
            CurrencyAccountTable
                .select(CurrencyAccountTable.id)
                .where { CurrencyAccountTable.id eq UUID.fromString(currencyAccount.id.raw) }
                .count() > 0

        if (exists) update(currencyAccount)
        else insert(currencyAccount, accountOwnerId)
    }

    private fun insert(currencyAccount: CurrencyAccount, accountOwnerId: AccountOwnerId) {
        CurrencyAccountTable.insert {
            it[id] = UUID.fromString(currencyAccount.id.raw)
            it[amount] = currencyAccount.money.amount.toString()
            it[currency] = currencyAccount.money.currency.currencyCode
            it[accountId] = UUID.fromString(accountOwnerId.raw)
        }
    }

    private fun update(currencyAccount: CurrencyAccount) {
        CurrencyAccountTable.update({ CurrencyAccountTable.id.eq(UUID.fromString(currencyAccount.id.raw)) }) {
            it[amount] = currencyAccount.money.amount.toString()
            it[currency] = currencyAccount.money.currency.currencyCode
        }
    }
}