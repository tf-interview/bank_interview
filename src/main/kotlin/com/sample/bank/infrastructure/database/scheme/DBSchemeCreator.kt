package com.sample.bank.infrastructure.database.scheme

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.account.FirstName
import com.sample.bank.domain.account.LastName
import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.accountdata.AccountData
import com.sample.bank.domain.accountdata.CurrencyAccountData
import com.sample.bank.domain.exchange.CurrencyAccountId
import com.sample.bank.infrastructure.database.model.AccountOwnerTable
import com.sample.bank.infrastructure.database.model.CurrencyAccountTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Component
@Transactional
class DBSchemeCreator : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        SchemaUtils.create(AccountOwnerTable, CurrencyAccountTable)
        DBInitialDataInserter.insert()
    }
}

object DBInitialDataInserter {

    fun insert() {
        AccountOwnerTable.insert {
            it[id] = UUID.fromString(fakeData.id.raw)
            it[firstName] = fakeData.firstName.raw
            it[lastName] = fakeData.lastName.raw
            it[pesel] = fakeData.pesel.raw
        }

        fakeData.accounts.forEach { fakeDataAccount ->
            CurrencyAccountTable.insert {
                it[id] = UUID.fromString(fakeDataAccount.id.raw)
                it[currency] = fakeDataAccount.currency.currencyCode
                it[amount] = fakeDataAccount.amount.toString()
                it[accountId] = UUID.fromString(fakeData.id.raw)
            }
        }
    }

    val fakeData = AccountData(
        id = AccountOwnerId(UUID.randomUUID().toString()),
        firstName = FirstName("Gerwazy"),
        lastName = LastName("Abacki"),
        pesel = Pesel("63031599879"),
        accounts = listOf(
            CurrencyAccountData(
                id = CurrencyAccountId(UUID.randomUUID().toString()),
                amount = BigDecimal("12.50"),
                currency = Currency.getInstance("PLN")
            ),
            CurrencyAccountData(
                id = CurrencyAccountId(UUID.randomUUID().toString()),
                amount = BigDecimal("200001.37"),
                currency = Currency.getInstance("USD")
            )
        )
    )
}