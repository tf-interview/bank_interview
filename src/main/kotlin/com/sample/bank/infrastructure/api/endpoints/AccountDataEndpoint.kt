package com.sample.bank.infrastructure.api.endpoints

import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.accountdata.AccountData
import com.sample.bank.domain.accountdata.AccountDataQuery
import com.sample.bank.domain.accountdata.CurrencyAccountData
import com.sample.bank.getLoggerForClass
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountDataEndpoint(private val query: AccountDataQuery) {

    @GetMapping("/accounts/{pesel}", produces = ["application/json"])
    fun getAccountData(@PathVariable("pesel") pesel: String): AccountDataJson {
        log.info("fetching account data for $pesel")
        return query.findAccountDataByPesel(Pesel(pesel)).toJson()
    }

    companion object {
        private val log = getLoggerForClass()
    }
}

private fun AccountData.toJson() = AccountDataJson(
    pesel = pesel.raw,
    firstName = firstName.raw,
    lastName = lastName.raw,
    accounts = accounts.map { it.toJson() }
)

private fun CurrencyAccountData.toJson() = CurrencyAccountJson(
    currency = currency.currencyCode,
    amount = amount.toString()
)

class AccountDataJson(
    val pesel: String,
    val firstName: String,
    val lastName: String,
    val accounts: List<CurrencyAccountJson>
)

class CurrencyAccountJson(
    val currency: String,
    // format amount
    val amount: String
)