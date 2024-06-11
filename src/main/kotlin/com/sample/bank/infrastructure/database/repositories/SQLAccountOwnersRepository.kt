package com.sample.bank.infrastructure.database.repositories

import com.sample.bank.domain.account.*
import com.sample.bank.domain.ports.AccountOwnersRepository
import com.sample.bank.infrastructure.database.model.AccountOwnerTable
import org.jetbrains.exposed.sql.insert
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Repository
class SQLAccountOwnersRepository : AccountOwnersRepository {
    override fun findByPesel(pesel: Pesel): AccountOwner? {
        return AccountOwnerTable.select(
            AccountOwnerTable.id,
            AccountOwnerTable.pesel,
            AccountOwnerTable.firstName,
            AccountOwnerTable.lastName,
        ).where { AccountOwnerTable.pesel eq pesel.raw }
            .firstOrNull()
            ?.run {
                AccountOwner(
                    id = AccountOwnerId(this[AccountOwnerTable.id].toString()),
                    firstName = FirstName(this[AccountOwnerTable.firstName]),
                    lastName = LastName(this[AccountOwnerTable.lastName]),
                    pesel = Pesel(this[AccountOwnerTable.pesel]),
                )
            }
    }

    override fun insert(createdAccountOwner: AccountOwner) {
        AccountOwnerTable.insert {
            it[id] = UUID.fromString(createdAccountOwner.id.raw)
            it[firstName] = createdAccountOwner.firstName.raw
            it[lastName] = createdAccountOwner.lastName.raw
            it[pesel] = createdAccountOwner.pesel.raw
        }
    }
}