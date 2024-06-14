package com.sample.bank.infrastructure.database.repositories

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.account.FirstName
import com.sample.bank.domain.account.LastName
import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.accountdata.AccountData
import com.sample.bank.domain.accountdata.CurrencyAccountData
import com.sample.bank.domain.exchange.CurrencyAccountId
import com.sample.bank.domain.ports.AccountDataReadOnlyRepository
import com.sample.bank.domain.ports.AccountOwnerNotFoundException
import com.sample.bank.infrastructure.database.model.AccountOwnerTable
import com.sample.bank.infrastructure.database.model.CurrencyAccountTable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Repository
class SQLAccountDataReadOnlyRepository : AccountDataReadOnlyRepository {

    @Transactional(readOnly = true)
    override fun getByPesel(pesel: Pesel): AccountData =
        findByPesel(pesel) ?: throw AccountOwnerNotFoundException(pesel)

    @Transactional(readOnly = true)
    override fun findByPesel(pesel: Pesel): AccountData? {
        return (AccountOwnerTable innerJoin CurrencyAccountTable).select(
            AccountOwnerTable.id,
            AccountOwnerTable.pesel,
            AccountOwnerTable.firstName,
            AccountOwnerTable.lastName,
            CurrencyAccountTable.id,
            CurrencyAccountTable.amount,
            CurrencyAccountTable.currency
        ).where { AccountOwnerTable.pesel eq pesel.raw }
            .let {
                val first = it.firstOrNull() ?: return null
                AccountData(
                    id = AccountOwnerId(first[AccountOwnerTable.id].toString()),
                    firstName = FirstName(first[AccountOwnerTable.firstName]),
                    lastName = LastName(first[AccountOwnerTable.lastName]),
                    pesel = Pesel(first[AccountOwnerTable.pesel]),
                    accounts = it.map {
                        CurrencyAccountData(
                            id = CurrencyAccountId(it[CurrencyAccountTable.id].toString()),
                            currency = Currency.getInstance(it[CurrencyAccountTable.currency]),
                            amount = BigDecimal(it[CurrencyAccountTable.amount])
                        )
                    }
                )
            }
    }
}
