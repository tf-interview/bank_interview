package com.sample.bank.domain.account.verification

import com.sample.bank.domain.account.RegisterAccountOwnerCommand
import com.sample.bank.getLoggerForClass
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus

@Component
class AccountRegistrationVerifiers(private val verifiers: List<AccountRegistrationVerifier>) {

    fun verify(command: RegisterAccountOwnerCommand) {
        val failureResults = verifiers.map { it.verify(command) }
            .filterIsInstance<VerificationResult.Failure>()

        if (failureResults.isEmpty()) {
            log.info("verification completed with success")
        } else {
            log.info("verification completed with failure")
            throw VerificationFailureException(failureResults.map { it.reason })
        }
    }

    companion object {
        private val log = getLoggerForClass()
    }
}

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class VerificationFailureException(val failures: List<VerificationFailureReason>) :
    RuntimeException(failures.toString())