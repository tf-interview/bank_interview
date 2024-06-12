package com.sample.bank.integration

import com.sample.bank.BankApplication
import com.sample.bank.domain.generators.PeselGenerator.validPesel
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [BankApplication::class, TestRestClientConfiguration::class, TestRestClient::class])
class BankApplicationTests @Autowired constructor(private val restClient: TestRestClient) {

    private val validPesel = validPesel()
    private val rate = 4

    @BeforeTest
    fun setup() {
        // mock nbp with given rate
    }

    @Test
    fun featureTest() {
        // account creation
        assertTrue { restClient.getAccountDataError(validPesel).statusCode == HttpStatus.NOT_FOUND }
        assertTrue { restClient.register(validPesel, "Janusz", "Abacki", "10").statusCode == HttpStatus.CREATED }
        //TODO check first name, last name and accounts
        assertTrue { restClient.getAccountData(validPesel).statusCode == HttpStatus.OK }
        assertTrue { restClient.register(validPesel, "Janusz", "Abacki", "10").statusCode == HttpStatus.CONFLICT }

        // exchange
        assertTrue { restClient.exchangePLN(validPesel, "4").statusCode == HttpStatus.OK }
        restClient.getAccountData(validPesel).body!!.accounts
            .onPlnAccount {
                assertTrue { amount == (10 - 4).toString() }
            }.onUsdAccount {
                assertTrue { amount == (4 * rate).toString() }
            }

        //TODO exchange with insufficient funds -> 400 / 409 / 422 ?
    }
}

private fun List<CurrencyAccountResponse>.onPlnAccount(function: CurrencyAccountResponse.() -> Unit) = also {
    this.first { it.currency == "PLN" }.also(function)
}

private fun List<CurrencyAccountResponse>.onUsdAccount(function: CurrencyAccountResponse.() -> Unit) = also {
    this.first { it.currency == "USD" }.also(function)
}
