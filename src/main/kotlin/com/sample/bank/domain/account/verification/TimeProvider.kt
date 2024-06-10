package com.sample.bank.domain.account.verification

import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class TimeProvider {
    fun today() = ZonedDateTime.now(ZoneId.of("ECT")).toLocalDate()
}