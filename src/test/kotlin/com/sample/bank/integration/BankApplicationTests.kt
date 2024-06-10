package com.sample.bank.integration

import com.sample.bank.BankApplication
import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.generators.PeselGenerator.validPesel
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
@ContextConfiguration(classes = [BankApplication::class, TestRestClientConfiguration::class, TestRestClient::class])
class BankApplicationTests @Autowired constructor(private val restClient: TestRestClient) {

    @Test
    fun shouldFetchAccountData() {
        val result = restClient.getAccountData(validPesel())

        assertNotNull(result)
        assertTrue { result.firstName == "Gerwazy" }
    }
}
