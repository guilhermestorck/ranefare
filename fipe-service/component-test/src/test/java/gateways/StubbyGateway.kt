package gateways

import conf.Config
import cucumber.api.DataTable
import domains.stubby.StubbyRequest
import domains.stubby.StubbyResponse
import gherkin.deps.com.google.gson.Gson
import gherkin.deps.com.google.gson.reflect.TypeToken
import khttp.responses.Response
import org.json.JSONTokener
import parsers.DataTableParser
import java.rmi.UnexpectedException
import java.util.regex.Pattern

object StubbyGateway {
    private val gson = Gson()

    fun create(serviceName: String, integrationName: String, dataTable: DataTable): Int {
        val stubbyRequest: StubbyRequest = DataTableParser.parseMockStubbyRequest(
            Config.MOCKED_API_NAMES.getHostApi(integrationName, serviceName), integrationName, serviceName, dataTable)

        val response = khttp.request(
            method = "POST",
            url = Config.MOCKED_API_NAMES.getHostAddress(integrationName),
            json = JSONTokener(gson.toJson(stubbyRequest)).nextValue()
        )

        return getStubbyId(response)
            ?: throw UnexpectedException("O Stubby não retornou um indicador para o mock que deveria ter sido criado.")
    }

    fun getAllServices(integrationName: String): List<StubbyResponse> {
        val jsonResponse = khttp.request(
            method = "GET",
            url = Config.MOCKED_API_NAMES.getHostAddress(integrationName)
        ).text
        return if (jsonResponse.isBlank()) arrayListOf()
        else {
            val listType = object : TypeToken<List<StubbyResponse>>() {}.type
            gson.fromJson(jsonResponse, listType)
        }
    }

    fun deleteAllServices() {
        Config.MOCKED_API_NAMES.hosts
            .filter { hostEntry -> hostEntry.value.mocked }
            .forEach { hostConfig ->
                deleteAllServices(hostConfig.key)
            }
    }

    private fun delete(integrationName: String, id: Int) {
        khttp.request(
            method = "DELETE",
            url = "${Config.MOCKED_API_NAMES.getHostAddress(integrationName)}/$id"
        )
    }

    private fun deleteAllServices(integrationName: String) {
        getAllServices(integrationName).forEach { service -> delete(integrationName, service.id) }
    }

    private fun getStubbyId(response: Response): Int? {
        val location = response.headers["location"].orEmpty()
        val matcher = Pattern.compile(".*?/(\\d+)").matcher(location)
        return if (matcher.find()) Integer.parseInt(matcher.group(1)) else null
    }
}
