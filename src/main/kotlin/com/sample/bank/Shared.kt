package com.sample.bank

import java.util.logging.Logger

inline fun <reified T> T.getLoggerForClass(): Logger =
    Logger.getLogger(T::class.java.enclosingClass.simpleName)