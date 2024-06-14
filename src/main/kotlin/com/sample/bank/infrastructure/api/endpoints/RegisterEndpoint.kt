package com.sample.bank.infrastructure.api.endpoints

import com.sample.bank.domain.account.*
import com.sample.bank.getLoggerForClass
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class RegisterEndpoint(private val registrationService: AccountOwnerRegistrationService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        value = ["/accounts/{pesel}"],
        consumes = ["application/json"]
    )
    fun registerAccount(
        @PathVariable("pesel") pesel: String,
        @RequestBody request: RegisterAccountRequest
    ) {
        log.info("registering new account owner with $request and $pesel")
        registrationService.registerNewAccountOwner(request.toCommand(pesel))
    }

    companion object {
        private val log = getLoggerForClass()
    }
}

data class RegisterAccountRequest(val firstName: String, val lastName: String, val initialAmount: String)

fun RegisterAccountRequest.toCommand(pesel: String) =
    RegisterAccountOwnerCommand(
        pesel = Pesel(pesel),
        firstName = FirstName(firstName),
        lastName = LastName(firstName),
        initialAmount = BigDecimal(initialAmount)
    )