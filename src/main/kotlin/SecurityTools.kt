package com.rsk

import ConsoleOutputStrategy
import Hash
import Providers
import Sign
import com.rsk.security.ArgumentInitializers
import com.rsk.security.ArgumentType
import com.rsk.security.ParseArgs

fun main(args: Array<String>) {
    val isHelp = if (args.isNotEmpty()) args[0] else ""

    if (args.size < 2 || isHelp == "--help") {
        println("usage: java SecurityTools --help: hash|sign|providers")
    }

    if (isHelp.startsWith("--help")) {
        val helpOn = isHelp.split(":")[1]
        when (helpOn) {
            "providers" -> Providers.Help().help()
            "hash" -> Hash.Help().help()
            "sign" -> Sign.Help().help()
        }
        return
    }

    // all the flags that can be used
    ParseArgs.setupDefaultValues(
        arrayOf(ArgumentInitializers("operation", ArgumentType.StringType(), "-op"),
            ArgumentInitializers("algorithm", ArgumentType.StringType(), "-a"),
            ArgumentInitializers("keystoreType", ArgumentType.StringType("JKS"), "-keystoretype"),
            ArgumentInitializers("encode", ArgumentType.BooleanType(true), "-encode"),
            ArgumentInitializers("sign", ArgumentType.BooleanType(true), "-s"),
            ArgumentInitializers("verify", ArgumentType.BooleanType(), "-v"),
            ArgumentInitializers("keyStoreFilename", ArgumentType.StringType(), "-keystore"),
            ArgumentInitializers("keypass", ArgumentType.StringType(), "-keypass"),
            ArgumentInitializers("keyStorePass", ArgumentType.StringType(), "-keystorepass"),
            ArgumentInitializers("keyAlias", ArgumentType.StringType(), "-alias"),
            ArgumentInitializers("provider", ArgumentType.StringType(), "-p"),
            ArgumentInitializers("fileName", ArgumentType.StringType(), "-f"),
            ArgumentInitializers("destFileName", ArgumentType.StringType(), "-d"),
            ArgumentInitializers("signatureFileName", ArgumentType.StringType(), "-sigfilename"),
            ArgumentInitializers("overwrite", ArgumentType.BooleanType(), "-o"),
            ArgumentInitializers("decode", ArgumentType.BooleanType(), "-decode"),
            ArgumentInitializers("filter", ArgumentType.StringType(), "-filter")
        ))

    ParseArgs(args)

    val type: ArgumentType.StringType = ParseArgs.arguments.get("operation")!!.type as ArgumentType.StringType

    // execute the code
    when (type.value.toLowerCase()) {
        "hash" -> {
            Hash(ConsoleOutputStrategy()).run()
        }

        "sign" -> {
            Sign(ConsoleOutputStrategy()).run()
        }

        "providers" -> {
            Providers(ConsoleOutputStrategy()).run()
        }
    }
}