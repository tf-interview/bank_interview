package com.sample.bank.domain.ports

import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.exchange.CurrencyAccount
import com.sample.bank.domain.exchange.CurrencyAccounts

interface CurrencyAccountsRepository {
    fun findByAccountOwnerId(accountOwnerId: AccountOwnerId): CurrencyAccounts
    fun saveOrUpdate(currencyAccount: CurrencyAccount, accountOwnerId: AccountOwnerId)
}
