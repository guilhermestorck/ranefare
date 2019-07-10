package gateways

import conf.Hosts
import cucumber.api.DataTable
import domains.stubby.StubbyResponse
import gherkin.deps.com.google.gson.Gson
import gherkin.deps.com.google.gson.reflect.TypeToken
import khttp.responses.Response
import parsers.DataTableParser
import java.rmi.UnexpectedException
import java.util.*
import java.util.regex.Pattern


object StubbyGateway {
    private val gson = Gson()
    private val APIS = mapOf(
        "obter marcas de carros" to "/carros/marcas.json",
        "obter veículos de uma marca" to "/carros/veiculos/{brandId}.json",
        "obter modelos de um veículo" to "/carros/veiculo/{brandId}/{vehicleId}.json",
        "obter detalhes de um modelo" to "/carros/veiculo/{brandId}/{vehicleId}/{modelId}.json"
    )

    fun create(apiName: String, dataTable: DataTable): Int {
        val stubbyRequest =
            DataTableParser.parseMockRequestDataTable(
                APIS[apiName],
                apiName, dataTable)

        val response = khttp.request(
            method = "POST",
            url = Hosts.MOCKS_FIPE.address,
            data = gson.toJson(stubbyRequest)
        )

        return getStubbyId(response) ?: throw UnexpectedException("The stubby didn't return an identifier to mock.")
    }

    fun delete(id: Int) {
        khttp.request(
            method = "DELETE",
            url = "${Hosts.MOCKS_FIPE.address}/$id"
        )
    }

    fun getService(id: Int): StubbyResponse =
        gson.fromJson(khttp.request(
            method = "GET",
            url = "${Hosts.MOCKS_FIPE.address}/$id"
        ).text, StubbyResponse::class.java)

    fun getAllServices(): List<StubbyResponse> {
        val jsonResponse = khttp.request(
            method = "GET",
            url = Hosts.MOCKS_FIPE.address
        ).text
        if (jsonResponse.isBlank()) return arrayListOf()
        else {
            val listType = object : TypeToken<ArrayList<StubbyResponse>>() {}.type
            return gson.fromJson(jsonResponse, listType)
        }
    }

    fun deleteAllServices() {
        getAllServices().forEach { service -> delete(service.id) }
    }

    private fun getStubbyId(response: Response): Int? {
        val location = response.headers["location"].orEmpty()
        val matcher = Pattern.compile(".*?/(\\d+)").matcher(location)
        return if (matcher.find()) Integer.parseInt(matcher.group(1)) else null
    }
}
