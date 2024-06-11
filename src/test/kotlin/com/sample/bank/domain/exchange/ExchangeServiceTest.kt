package com.sample.bank.domain.exchange

import com.sample.bank.domain.TestCurrencies.PLN
import com.sample.bank.domain.TestCurrencies.USD
import com.sample.bank.domain.account.AccountOwnerId
import com.sample.bank.domain.generators.CurrencyAccountGenerator.currencyAccount
import com.sample.bank.domain.generators.DoExchangeCommandGenerator.doExchangeCommand
import com.sample.bank.domain.generators.MoneyGenerator.pln
import com.sample.bank.domain.generators.MoneyGenerator.usd
import com.sample.bank.domain.ports.CurrencyAccountsRepository
import com.sample.bank.domain.ports.ExchangeRate
import com.sample.bank.domain.ports.ExchangeRateProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.check
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.math.BigDecimal
import kotlin.test.assertTrue

class ExchangeServiceTest {

    private val accountOwnerId = AccountOwnerId("QWERTYU")
    private val currencyAccountPLN = currencyAccount(money = pln("4"))
    private val currencyAccountUSD = currencyAccount(money = usd("0"))
    private val currencyAccounts = CurrencyAccounts(accountOwnerId, listOf(currencyAccountPLN, currencyAccountUSD))

    private val exchangeRate = ExchangeRate(from = PLN, to = USD, rate = BigDecimal("3.65"))
    private val currencyAccountsRepository = mock<CurrencyAccountsRepository> {
        on { it.findByAccountOwnerId(accountOwnerId) } doReturn (currencyAccounts)
    }
    private val exchangeRateProvider = mock<ExchangeRateProvider> {
        on { it.getExchangeRate() } doReturn (exchangeRate)
    }
    private val service = ExchangeService(currencyAccountsRepository, exchangeRateProvider)

    @Test
    fun shouldExchangeMoney() {
        val command = doExchangeCommand(accountOwnerId)

        service.doExchange(command)

        verify(currencyAccountsRepository).saveOrUpdate(check {
            assertTrue { it.money == usd("3.65") }
        }, accountOwnerId)
        verify(currencyAccountsRepository).saveOrUpdate(check {
            assertTrue { it.money == pln("3.00") }
        }, accountOwnerId)
    }

    @Test
    fun shouldThrowWhenInsufficientFunds() {
        val command = doExchangeCommand(accountOwnerId, amount = "4.01")

        assertThrows<IllegalArgumentException> {
            service.doExchange(command)
        }
    }

    @Test
    fun shouldCreateCurrencyAccountWhenItDoesNotExists() {
    }
}
