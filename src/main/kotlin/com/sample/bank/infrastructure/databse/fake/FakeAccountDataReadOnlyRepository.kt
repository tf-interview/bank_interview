package com.sample.bank.infrastructure.databse.fake

import com.sample.bank.domain.account.Pesel
import com.sample.bank.domain.accountdata.AccountData
import com.sample.bank.domain.ports.AccountDataReadOnlyRepository
import org.springframework.stereotype.Repository

@Repository
class FakeAccountDataReadOnlyRepository : AccountDataReadOnlyRepository {
    override fun getByPesel(pesel: Pesel): AccountData {
        TODO("Not yet implemented")
    }

}