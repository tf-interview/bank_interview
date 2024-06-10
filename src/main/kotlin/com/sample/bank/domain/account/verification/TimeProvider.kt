package com.sample.bank.domain.account.verification

import java.time.ZoneId
import java.time.ZonedDateTime

class TimeProvider {
    fun today() = ZonedDateTime.now(ZoneId.of("ECT")).toLocalDate()
}