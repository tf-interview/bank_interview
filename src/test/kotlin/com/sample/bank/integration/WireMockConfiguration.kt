package com.sample.bank.integration

import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class WireMockExtension : BeforeAllCallback, AfterAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
        SpringExtension.getApplicationContext(context!!)
            .getBeansOfType(WireMockServer::class.java).values.firstOrNull()
            ?.start()
    }

    override fun afterAll(context: ExtensionContext?) {
        SpringExtension.getApplicationContext(context!!)
            .getBeansOfType(WireMockServer::class.java).values.firstOrNull()
            ?.stop()
    }
}