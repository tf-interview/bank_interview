package com.sample.bank

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [BankApplication::class])
class BankApplicationTests {

    @Test
    fun contextLoads() {
    }

}
