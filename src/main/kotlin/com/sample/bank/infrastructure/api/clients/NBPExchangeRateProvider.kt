package com.sample.bank.infrastructure.api.clients

import com.sample.bank.domain.ports.ExchangeRate
import com.sample.bank.domain.ports.ExchangeRateProvider
import com.sample.bank.getLoggerForClass
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.math.BigDecimal
import java.util.*

@Component
class NBPExchangeRateProvider(private val client: NBPClient) : ExchangeRateProvider {
    override fun getExchangeRate(): ExchangeRate {
        return client.fetchExchangeRate().toExchangeRate()
    }
}

private fun NBPExchageRate.toExchangeRate() = ExchangeRate(
    from = Currency.getInstance("USD"),
    to = Currency.getInstance("PLN"),
    rate = BigDecimal(this.rates.first().mid)
)

@Component
class NBPClient {

    fun fetchExchangeRate(): NBPExchageRate {
        val response = RestTemplate().getForEntity<NBPExchageRate>(url)
        log.info("exchange rate from NBP $response")
        return response.body ?: throw NBPClientException(response.statusCode)
    }

    companion object {
        private val log = getLoggerForClass()
        private val url = "https://api.nbp.pl/api/exchangerates/rates/a/usd/"
    }
}

data class NBPExchageRate(
    val rates: List<NBPRates>
)

data class NBPRates(val mid: String)

class NBPClientException(val code: HttpStatusCode) : RuntimeException()