package com.sample.bank.domain.ports

import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.accountdata.AccountData

interface AccountDataReadOnlyRepository {
    fun getByPesel(pesel: Pesel): AccountData
}