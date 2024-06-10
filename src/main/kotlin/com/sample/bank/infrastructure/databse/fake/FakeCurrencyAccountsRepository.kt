package com.sample.bank.infrastructure.databse.fake

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.exchange.CurrencyAccount
import com.sample.bank.domain.exchange.CurrencyAccounts
import com.sample.bank.domain.ports.CurrencyAccountsRepository
import org.springframework.stereotype.Repository

@Repository
class FakeCurrencyAccountsRepository: CurrencyAccountsRepository {
    override fun findByAccountOwnerId(accountOwnerId: AccountOwnerId): CurrencyAccounts {
        TODO("Not yet implemented")
    }

    override fun saveOrUpdate(currencyAccount: CurrencyAccount) {
        TODO("Not yet implemented")
    }
}