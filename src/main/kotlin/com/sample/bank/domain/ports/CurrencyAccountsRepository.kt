package com.sample.bank.domain.ports

import com.sample.bank.domain.AccountOwnerId
import com.sample.bank.domain.CurrencyAccounts

interface CurrencyAccountsRepository {
    fun findByAccountOwnerId(accountOwnerId: AccountOwnerId): CurrencyAccounts
}
