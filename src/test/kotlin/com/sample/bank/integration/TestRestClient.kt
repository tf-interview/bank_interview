package com.sample.bank.integration

import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.ResponseEntity

@TestComponent
class TestRestClient(private val restTemplate: TestRestTemplate) {

    fun getAccountData(pesel: String): ResponseEntity<AccountDataResponse> {
        return restTemplate.getForEntity<AccountDataResponse>("/accounts/$pesel")
    }

    fun getAccountDataError(pesel: String): ResponseEntity<Any> {
        return restTemplate.getForEntity<Any>("/accounts/$pesel")
    }

    fun register(pesel: String, firstName: String, lastName: String, initialAmount: String): ResponseEntity<Any> {
        return restTemplate.postForEntity<Any>("/accounts/$pesel", RegisterAccountOwnerRequest(firstName, lastName, initialAmount))
    }

    fun exchangeUSD(pesel: String, amount: String): ResponseEntity<Any> {
        return restTemplate.postForEntity<Any>("/accounts/$pesel/exchange", ExchangeRequest("USD", "PLN", amount))
    }

    fun exchangePLN(pesel: String, amount: String): ResponseEntity<Any> {
        return restTemplate.postForEntity<Any>("/accounts/$pesel/exchange", ExchangeRequest("PLN", "USD", amount))
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

data class RegisterAccountOwnerRequest(
    val firstName: String,
    val lastName: String,
    val initialAmount: String
)

data class ExchangeRequest(
    val sourceCurrency: String,
    val destinationCurrency: String,
    val amount: String
)