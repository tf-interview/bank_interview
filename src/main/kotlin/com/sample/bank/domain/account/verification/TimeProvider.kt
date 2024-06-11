package com.sample.bank.domain.account.verification

import org.springframework.stereotype.Component
import java.time.Clock
import java.time.ZonedDateTime

@Component
class TimeProvider {
    fun today() = ZonedDateTime.now(Clock.systemDefaultZone()).toLocalDate()
}