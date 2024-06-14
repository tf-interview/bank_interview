package com.sample.bank.integration

import com.github.tomakehurst.wiremock.WireMockServer
import com.sample.bank.infrastructure.api.clients.NBPClientProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.util.TestSocketUtils

@TestConfiguration
class TestRestClientConfiguration {

    //  inject port - make it random

    @Bean
    fun testRestTemplate(): TestRestTemplate = TestRestTemplate(
        RestTemplateBuilder()
            .rootUri("http://localhost:8080/")
    )
}

@TestConfiguration
class TestWiremockConfiguration {

    private val port = TestSocketUtils.findAvailableTcpPort()

    @Primary
    @Bean
    fun testNBPClientProperties(): NBPClientProperties =
        object : NBPClientProperties {
            override var url: String = "http://localhost:$port"
        }

    @Bean
    fun wireMockServer(): WireMockServer = WireMockServer(port)
}
