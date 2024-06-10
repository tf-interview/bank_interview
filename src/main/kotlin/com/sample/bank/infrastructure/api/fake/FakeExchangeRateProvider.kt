package com.sample.bank.infrastructure.api.fake

import com.sample.bank.domain.ports.ExchangeRate
import com.sample.bank.domain.ports.ExchangeRateProvider
import org.springframework.stereotype.Component

@Component
class FakeExchangeRateProvider: ExchangeRateProvider {
    override fun getExchangeRate(): ExchangeRate {
        TODO("Not yet implemented")
    }
}