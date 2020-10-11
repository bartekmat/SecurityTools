package com.rsk.arguments

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object Argument {
    fun <T> argument():
            ReadOnlyProperty<Any, T> = object : ReadOnlyProperty<Any, T> {
        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return ParseArgs[property.name] as T
        }
    }
}