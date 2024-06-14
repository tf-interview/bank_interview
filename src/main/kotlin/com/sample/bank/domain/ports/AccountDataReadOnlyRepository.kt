package com.sample.bank.domain.ports

import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.accountdata.AccountData
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

interface AccountDataReadOnlyRepository {
    fun findByPesel(pesel: Pesel): AccountData?
    fun getByPesel(pesel: Pesel): AccountData
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class AccountOwnerNotFoundException(val pesel: Pesel) :
    RuntimeException("Account owner with pesel ${pesel.raw} not found")
