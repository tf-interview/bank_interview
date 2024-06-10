package com.sample.bank

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.getLoggerForClass(): Logger =
    LoggerFactory.getLogger(T::class.java.enclosingClass)