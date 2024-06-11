package com.sample.bank.infrastructure.database.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object AccountOwnerTable : Table() {
    val id: Column<UUID> = uuid("id")
    val pesel: Column<String> = varchar("pesel", 11).uniqueIndex()
    val firstName: Column<String> = varchar("first_name", 50)
    val lastName: Column<String> = varchar("last_name", 50)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

object CurrencyAccountTable : Table() {
    val id: Column<UUID> = uuid("id")
    val amount: Column<String> = varchar("amount", 50)
    val currency: Column<String> = varchar("currency", 3)
    val accountId: Column<UUID> = uuid("account_id").references(AccountOwnerTable.id).index()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
