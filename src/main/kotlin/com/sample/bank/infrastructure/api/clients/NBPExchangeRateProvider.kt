package com.sample.bank.infrastructure.api.clients

import com.sample.bank.domain.ports.ExchangeRate
import com.sample.bank.domain.ports.ExchangeRateProvider
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
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

class NBPClientException(val code: HttpStatusCode) : RuntimeException()