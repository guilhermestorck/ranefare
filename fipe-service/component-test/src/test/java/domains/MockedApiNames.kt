package domains

import conf.Config

data class MockedApiNames(
    val hosts: Map<String, HostConfig>
) {
    data class HostConfig(
        val mocked: Boolean = false,
        val default: String,
        val services: Map<String, String>
    )

    fun getHostAddress(hostName: String): String {
        val envName = "gateways_${
        if (hosts.getValue(hostName).mocked) "mocks_$hostName" else hostName
        }_endpoint"
        return Config.getEnv(envName, hosts.getValue(hostName).default)
    }

    fun getHostApi(hostName: String, serviceName: String) =
        hosts.getValue(hostName.replace(' ', '-'))
            .services.getValue(serviceName.replace(' ', '-'))

    fun getHostUrl(hostName: String, serviceName: String) =
        getHostAddress(hostName).plus(getHostApi(hostName, serviceName))
}

