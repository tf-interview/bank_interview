package com.sample.bank.domain.account

import java.time.LocalDate
import java.util.*

data class AccountOwner(
    val id: AccountOwnerId,
    val pesel: Pesel,
    val firstName: FirstName,
    val lastName: LastName
) {
    companion object {
        fun create(command: RegisterAccountOwnerCommand) = AccountOwner(
            id = AccountOwnerId(UUID.randomUUID().toString()),
            pesel = command.pesel,
            firstName = command.firstName,
            lastName = command.lastName
        )
    }
}

data class AccountOwnerId(val raw: String)
data class FirstName(val raw: String)
data class LastName(val raw: String)
data class Pesel(val raw: String) {

    init {
        require(raw.matches(Regex("[0-9]+"))) { "pesel $raw should contain only digits" }
        require(raw.length == 11) { "pesel with ${raw.length} digits is not valid" }
        // validate correct day, month, check digit
    }

    fun birthDate(): LocalDate {
        val year = raw.substring(0, 2).toInt() + 1900
        val month = raw.substring(2, 4).toInt()
        val day = raw.substring(4, 6).toInt()
        return LocalDate.of(year, month, day)
    }
}
