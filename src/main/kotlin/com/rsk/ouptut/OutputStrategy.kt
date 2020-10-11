package com.rsk.ouptut

interface OutputStrategy {
    fun write(value: String)
    fun writeHeader()
    fun writeFooter()
}
