package com.rsk.arguments

sealed class ArgumentType {
    class StringType(var value: String = "") : ArgumentType()
    class BooleanType(var value: Boolean = false) : ArgumentType()
}