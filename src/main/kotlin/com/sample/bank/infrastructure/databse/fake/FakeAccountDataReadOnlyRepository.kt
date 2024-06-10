package com.sample.bank.infrastructure.databse.fake

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.account.FirstName
import com.sample.bank.domain.account.LastName
import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.accountdata.AccountData
import com.sample.bank.domain.accountdata.CurrencyAccountData
import com.sample.bank.domain.exchange.CurrencyAccountId
import com.sample.bank.domain.ports.AccountDataReadOnlyRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.Currency

@Repository
class FakeAccountDataReadOnlyRepository : AccountDataReadOnlyRepository {
    override fun getByPesel(pesel: Pesel): AccountData {
        return AccountData(
            id = AccountOwnerId("----"),
            firstName = FirstName("Gerwazy"),
            lastName = LastName("Abacki"),
            pesel = Pesel("63031599879"),
            accounts = listOf(
                CurrencyAccountData(
                    id = CurrencyAccountId("---1"),
                    amount = BigDecimal("12.50"),
                    currency = Currency.getInstance("PLN")
                ),
                CurrencyAccountData(
                    id = CurrencyAccountId("---2"),
                    amount = BigDecimal("200001.37"),
                    currency = Currency.getInstance("USD")
                )
            )
        )
    }

}