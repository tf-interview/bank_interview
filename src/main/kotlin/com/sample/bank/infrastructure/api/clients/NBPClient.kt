package com.sample.bank.infrastructure.api.clients

import com.sample.bank.getLoggerForClass
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@Component
class NBPClient(private val properties: NBPClientProperties) {

    fun fetchExchangeRate(): NBPExchageRate {
        val response = RestTemplate().getForEntity<NBPExchageRate>(properties.url + path)
        log.info("exchange rate from NBP $response")
        return response.body ?: throw NBPClientException(response.statusCode)
    }

    companion object {
        private val path = "/api/exchangerates/rates/a/usd/"
        private val log = getLoggerForClass()
    }
}

data class NBPExchageRate(
    val rates: List<NBPRates>
)

data class NBPRates(val mid: String)


interface NBPClientProperties {
    var url: String
}

@ConfigurationProperties(prefix = "clients.nbp")
class YamlBasedNBPClientProperties : NBPClientProperties {
    override lateinit var url: String
}