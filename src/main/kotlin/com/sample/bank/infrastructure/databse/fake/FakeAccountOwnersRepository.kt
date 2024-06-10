package com.sample.bank.infrastructure.databse.fake

import com.sample.bank.domain.account.AccountOwner
import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.ports.AccountOwnersRepository
import org.springframework.stereotype.Repository

@Repository
class FakeAccountOwnersRepository: AccountOwnersRepository {
    override fun findByPesel(pesel: Pesel): AccountOwner? {
        TODO("Not yet implemented")
    }

    override fun findById(accountOwnerId: AccountOwnerId): AccountOwner? {
        TODO("Not yet implemented")
    }

    override fun saveOrUpdate(createdAccountOwner: AccountOwner) {
        TODO("Not yet implemented")
    }
}