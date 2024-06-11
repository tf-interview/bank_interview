package com.sample.bank.domain.ports

import com.sample.bank.domain.account.AccountOwner
import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.account.Pesel

interface AccountOwnersRepository {
    fun findByPesel(pesel: Pesel): AccountOwner?
    fun insert(createdAccountOwner: AccountOwner)
}