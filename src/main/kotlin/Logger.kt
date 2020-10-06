package com.rsk.security

import java.util.logging.Logger


interface Loggable

fun Loggable.logger(): Logger {
    return Logger.getLogger(unwrapCompanionClass(this.javaClass).name)
}

fun <R : Any> R.logger(): Lazy<Logger> {
    return lazy { Logger.getLogger(unwrapCompanionClass(this.javaClass).name) }
}
