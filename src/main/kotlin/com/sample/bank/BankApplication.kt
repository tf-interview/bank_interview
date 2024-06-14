package com.sample.bank

import com.sample.bank.infrastructure.api.clients.YamlBasedNBPClientProperties
import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [BankApplication::class])
@ImportAutoConfiguration(ExposedAutoConfiguration::class)
@EnableConfigurationProperties(YamlBasedNBPClientProperties::class)
class BankApplication

fun main(args: Array<String>) {
    runApplication<BankApplication>(*args)
}
