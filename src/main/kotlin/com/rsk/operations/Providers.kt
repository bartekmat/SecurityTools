package com.rsk.operations

import com.rsk.arguments.Argument.argument
import com.rsk.models.ProviderDetails
import com.rsk.ouptut.OutputStrategy
import java.security.Provider
import java.security.Security


class Providers(outputStrategy: OutputStrategy) : SecurityBase(outputStrategy) {

    private val filter: String by argument()

    object Help {
        fun help() {
            println("providers: java SecurityToolsKt [-op 'providers'] [-f filename] [-d destfilename] [-p provider] [-a algorithm] [-o] [-encode]")
        }
    }

    override fun run() {
        listAllProviders()
    }

    private fun listAllProviders() {
        if (filter.isEmpty()) {
            getProviders().forEach {
                display(it)
            }
        } else {
            getFilteredProviders().forEach {
                display("${it.providerName}: ${it.name}")
            }
        }
    }

    private fun display(provider: Provider) {
        outputStrategy.write(provider.name)
        outputStrategy.writeHeader()
        provider.entries.forEach { entry ->
            outputStrategy.write("\t ${entry.key}, ${entry.value}")
        }
        outputStrategy.writeFooter()
    }

    private fun display(message: String) {
        outputStrategy.write(message)
    }

    private fun getProviders(): List<Provider> {
        val providers = Security.getProviders()
        val list = providers.asList()
        return list
    }

    private fun getFilteredProviders(): List<ProviderDetails> {
        return Security.getProviders().flatMap { provider ->
            provider.entries
                .filter { it -> it.key.toString().contains(filter, true) }
                .map { ProviderDetails(provider.name, it.key.toString()) }
        }
    }
}