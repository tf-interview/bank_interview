package com.sample.bank.integration

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.serverError
import com.sample.bank.BankApplication
import com.sample.bank.domain.generators.PeselGenerator.validPesels
import com.sample.bank.infrastructure.api.clients.NBPExchageRate
import com.sample.bank.infrastructure.api.clients.NBPRates
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

@ActiveProfiles("integration")
@ExtendWith(WireMockExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [BankApplication::class, TestRestClientConfiguration::class, TestRestClient::class, TestWiremockConfiguration::class])
class BankApplicationTests @Autowired constructor(
    private val restClient: TestRestClient,
    private val wireMockServer: WireMockServer
) {

    private var validPesel = validPesels.random()
    private val rate = "4.21"

    @BeforeTest
    fun setup() {
        wireMockServer.stubFor(
            WireMock.get("/api/exchangerates/rates/a/usd/")
                .willReturn(okForJson(NBPExchageRate(listOf(NBPRates(rate)))))
        )
    }

    @Test
    fun featureTest() {
        // account creation
        assertTrue { restClient.getAccountDataError(validPesel).statusCode == HttpStatus.NOT_FOUND }
        assertTrue { restClient.register(validPesel, "Janusz", "Abacki", "10").statusCode == HttpStatus.CREATED }
        //TODO check first name, last name and accounts
        assertTrue { restClient.getAccountData(validPesel).statusCode == HttpStatus.OK }
        assertTrue { restClient.register(validPesel, "Janusz", "Abacki", "10").statusCode == HttpStatus.BAD_REQUEST }

        // exchange
        assertTrue { restClient.exchangePLN(validPesel, "4.01").statusCode == HttpStatus.OK }
        restClient.getAccountData(validPesel).body!!.accounts
            .onPlnAccount {
                assertTrue { amount == "5.99" }
            }.onUsdAccount {
                assertTrue { amount == "0.95" }
            }

        // exchange insufficient funds
        assertTrue { restClient.exchangePLN(validPesel, "7").statusCode == HttpStatus.UNPROCESSABLE_ENTITY }
    }

    @Test
    fun shouldReturn500WhenErrorFromNBP() {
        wireMockServer.stubFor(WireMock.get("/api/exchangerates/rates/a/usd/").willReturn(serverError()))
        assertTrue { restClient.register(validPesel, "Janusz", "Abacki", "10").statusCode == HttpStatus.CREATED }

        assertTrue { restClient.exchangePLN(validPesel, "4.00").statusCode == HttpStatus.INTERNAL_SERVER_ERROR }
    }
}

private fun List<CurrencyAccountResponse>.onPlnAccount(function: CurrencyAccountResponse.() -> Unit) = also {
    this.first { it.currency == "PLN" }.also(function)
}

private fun List<CurrencyAccountResponse>.onUsdAccount(function: CurrencyAccountResponse.() -> Unit) = also {
    this.first { it.currency == "USD" }.also(function)
}
