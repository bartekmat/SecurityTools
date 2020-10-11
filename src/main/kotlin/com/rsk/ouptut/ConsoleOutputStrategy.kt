package com.rsk.ouptut

class ConsoleOutputStrategy : OutputStrategy {
    override fun write(value: String) {
        println(value)
    }

    override fun writeHeader() {
        println("-------------------------------------------------")
    }

    override fun writeFooter() {
        println("-------------------------------------------------")
    }
}