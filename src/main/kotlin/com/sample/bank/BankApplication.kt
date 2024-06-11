package com.sample.bank

import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [BankApplication::class])
@ImportAutoConfiguration(ExposedAutoConfiguration::class)
class BankApplication

fun main(args: Array<String>) {
    runApplication<BankApplication>(*args)
}
