package com.sample.bank.infrastructure.api.endpoints

import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.exchange.DoExchangeCommand
import com.sample.bank.domain.exchange.ExchangeService
import com.sample.bank.getLoggerForClass
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
class ExchangeEndpoint(private val exchangeService: ExchangeService) {

    @PostMapping(
        "/accounts/{pesel}/exchange",
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun exchange(
        @PathVariable("pesel") pesel: String,
        @RequestBody request: ExchangeRequest
    ) {
        log.info("exchanging with request $request")
        exchangeService.doExchange(request.toCommand(pesel))
    }

    companion object {
        private val log = getLoggerForClass()
    }
}

private fun ExchangeRequest.toCommand(pesel: String) = DoExchangeCommand(
    pesel = Pesel(pesel),
    sourceCurrency = Currency.getInstance(this.sourceCurrency),
    destinationCurrency = Currency.getInstance(this.destinationCurrency),
    amount = BigDecimal(this.amount)
)

data class ExchangeRequest(
    val sourceCurrency: String,
    val destinationCurrency: String,
    val amount: String
)