import com.rsk.security.Argument.argument
import java.security.Provider
import java.security.Security

class ProviderDetails(val providerName: String, val name: String)

class Providers(outPutStrategy: OutPutStrategy): SecurityBase(outPutStrategy) {

    private val filter: String by argument()

    class Help{
        fun help(){
            println("providers: java SecurityToolsKt [-op 'providers'] [-f filename] [-d destfilename] [-p provider] [-a algorithm] [-o] [-encode]")
        }
    }

    override fun run() {
        listAllProviders()
    }

    private fun listAllProviders() {
        if (filter.isEmpty()){
            getProviders().forEach {
                display(it)
            }
        }else {
            getFilteredProviders().forEach{
                display("${it.providerName}: ${it.name}")
            }
        }
    }

    private fun display(provider: Provider) {
        outPutStrategy.write(provider.name)
        outPutStrategy.writeHeader()
        provider.entries.forEach { entry ->
            outPutStrategy.write("\t ${entry.key}, ${entry.value}")
        }
        outPutStrategy.writeFooter()
    }
    private fun display(message: String){
        outPutStrategy.write(message)
    }

    private fun getProviders(): List<Provider> {
        val providers = Security.getProviders()
        val list = providers.asList()
        return list
    }

    private fun getFilteredProviders(): List<ProviderDetails>{
        return Security.getProviders().flatMap { provider ->
            provider.entries
                .filter { it -> it.key.toString().contains(filter, true) }
                .map { ProviderDetails(provider.name, it.key.toString()) }
        }
    }
}