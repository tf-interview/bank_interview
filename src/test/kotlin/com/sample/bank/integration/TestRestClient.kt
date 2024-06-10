package com.sample.bank.integration

import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject

@TestComponent
class TestRestClient(private val restTemplate: TestRestTemplate) {
    fun getAccountData(pesel: String): AccountDataResponse? {
        return restTemplate.getForObject<AccountDataResponse>("/accounts/$pesel")
    }
}

data class AccountDataResponse(
    val firstName: String,
    val lastName: String,
    val pesel: String,
    val accounts: List<CurrencyAccountResponse>
)

data class CurrencyAccountResponse(
    val currency: String,
    val amount: String
)


