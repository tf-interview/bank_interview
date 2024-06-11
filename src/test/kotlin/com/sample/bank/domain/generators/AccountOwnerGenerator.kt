package com.sample.bank.domain.generators

import com.sample.bank.domain.account.*
import com.sample.bank.domain.generators.PeselGenerator.validPesel

object AccountOwnerGenerator {
    fun accountOwner(
        id: AccountOwnerId = AccountOwnerId("123"),
        pesel: Pesel = Pesel(validPesel()),
        firstName: String = "Name",
        lastName: String = "Lastname"
    ) = AccountOwner(
        id = id,
        pesel = pesel,
        firstName = FirstName(firstName),
        lastName = LastName(lastName)
    )
}