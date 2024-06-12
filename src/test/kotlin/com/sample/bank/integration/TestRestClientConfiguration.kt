package com.sample.bank.integration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@TestConfiguration
class TestRestClientConfiguration {

    //  inject port - make it random

    @Bean
    fun testRestTemplate(): TestRestTemplate = TestRestTemplate(
        RestTemplateBuilder()
            .rootUri("http://localhost:8080/")
    )
}