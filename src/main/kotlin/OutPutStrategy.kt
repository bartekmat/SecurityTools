interface OutPutStrategy {
    fun write(value: String)
    fun writeHeader()
    fun writeFooter()
}

class ConsoleOutputStrategy : OutPutStrategy {
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