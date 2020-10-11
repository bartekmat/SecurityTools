package com.rsk.operations

import com.rsk.arguments.Argument.argument
import com.rsk.ouptut.OutputStrategy
import java.io.*
import java.util.*

abstract class SecurityBase(protected val outputStrategy: OutputStrategy) {
    abstract fun run()

    private val overwrite: Boolean by argument()
    private val encode: Boolean by argument()

    protected val algorithm: String by argument()
    protected val fileName: String by argument()
    protected val destFileName: String by argument()
    protected val provider: String by argument()

    @Throws(FileNotFoundException::class)
    fun createInputStream(fileName: String): InputStream {
        return if (fileName.isEmpty()){
            System.`in`
        }else {
            val file = File(fileName)
            if (file.exists()){
                FileInputStream(file)
            }else{
                throw FileNotFoundException()
            }
        }
    }

    @Throws(IOException::class)
    fun createOutputStream(fileName: String): OutputStream { //try to refactor this method it stinks
        return if (fileName.isEmpty()){
            System.out
        }else {
            val file = File(fileName)
            if (file.exists()){
                if (overwrite){
                    FileOutputStream(file)
                }else {
                    throw IOException("Destination file already exists")
                }
            }else {
                FileOutputStream(file)
            }
        }
    }

    @Throws(IOException::class)
    protected fun writeBytes(os: OutputStream, bytes: ByteArray) {
        if (encode) {
            val temp: String
            val encoder = Base64.getEncoder()
            temp = encoder.encodeToString(bytes)
            os.write(temp.toByteArray())
        } else {
            os.write(bytes)
        }
        os.flush()
    }

    @Throws(IOException::class)
    fun readBytes(inStream: InputStream): ByteArray {
        return inStream.readBytes()
    }
}