package com.rsk.operations

import com.rsk.logger.logger
import com.rsk.ouptut.OutputStrategy
import java.io.InputStream
import java.security.MessageDigest


class Hash(outputStrategy: OutputStrategy) : SecurityBase(outputStrategy) {

    private val logger by logger()

    object Help {
        fun help() {
            println("hashing: java SecurityToolsKt [-op 'hash'] [-f filename] [-d destfilename] [-p provider] [-a algorithm] [-o] [-encode]")
            println("\tf filename\t: read input data from filename")
            println("\td destFilename\t: write output hash to destFilename")
            println("\tp provider\t: use specific provider")
            println("\ta algorithm\t: algorithm to use for digest")
            println("\to\t\t: overwrite destfilename file")
            println("\te\t\t: BASE64 encode output")
        }
    }

    init {
        if (algorithm.isEmpty()) throw IllegalArgumentException()
    }

    override fun run() {
        val messageDigest = createDigestInstance(algorithm, provider)
        createInputStream(fileName).use { input ->
            createOutputStream(destFileName).use { output ->
                val hashedBytes = digestData(messageDigest, input)
                writeBytes(output, hashedBytes)
            }
        }
    }

    private fun digestData(md: MessageDigest, input: InputStream): ByteArray {
        val bytesToHash = readBytes(input)
        md.update(bytesToHash)
        return md.digest()
    }

    private fun createDigestInstance(algorithm: String, provider: String?): MessageDigest {
        return if (provider.isNullOrEmpty()) {
            MessageDigest.getInstance(algorithm)
        } else {
            MessageDigest.getInstance(algorithm, provider)
        }
    }
}