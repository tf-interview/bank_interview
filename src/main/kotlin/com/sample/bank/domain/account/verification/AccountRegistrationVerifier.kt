package com.sample.bank.domain.account.verification

import com.sample.bank.domain.account.RegisterAccountOwnerCommand
import com.sample.bank.domain.account.verification.VerificationFailureReason.NOT_ADULT
import com.sample.bank.domain.ports.AccountOwnersRepository
import java.time.Period

interface AccountRegistrationVerifier {
    fun verify(command: RegisterAccountOwnerCommand): VerificationResult
}

class AgeAccountRegistrationVerifier(private val clock: TimeProvider) : AccountRegistrationVerifier {
    override fun verify(command: RegisterAccountOwnerCommand): VerificationResult {
        val period = Period.between(command.pesel.birthDate(), clock.today())

        if (period.years < 18) return VerificationResult.Failure(NOT_ADULT)
        else return VerificationResult.Success
    }
}

class DuplicationAccountRegistrationVerifier(
    private val repository: AccountOwnersRepository
) : AccountRegistrationVerifier {

    override fun verify(command: RegisterAccountOwnerCommand): VerificationResult {
        repository.findByPesel(command.pesel)
        // verify is null
        return VerificationResult.Success
    }
}

sealed class VerificationResult {
    object Success : VerificationResult()
    class Failure(val reason: VerificationFailureReason) : VerificationResult()
}

enum class VerificationFailureReason {
    NOT_ADULT, ACCOUNT_ALREADY_EXISTS
}