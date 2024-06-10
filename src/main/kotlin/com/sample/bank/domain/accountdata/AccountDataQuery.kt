package com.sample.bank.domain.accountdata

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.account.FirstName
import com.sample.bank.domain.account.LastName
import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.exchange.CurrencyAccountId
import com.sample.bank.domain.ports.AccountDataReadOnlyRepository
import java.math.BigDecimal
import java.util.*

class AccountDataQuery(
    private val accountDataRepository: AccountDataReadOnlyRepository
) {

    fun findAccountDataByPesel(pesel: Pesel): AccountData {
        return accountDataRepository.getByPesel(pesel)
    }
}

data class AccountData(
    val id: AccountOwnerId,
    val pesel: Pesel,
    val firstName: FirstName,
    val lastName: LastName,
    val accounts: List<CurrencyAccountQuery>
)

data class CurrencyAccountQuery(
    val id: CurrencyAccountId,
    val amount: BigDecimal,
    val currency: Currency
)