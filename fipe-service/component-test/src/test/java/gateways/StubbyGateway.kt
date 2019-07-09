package gateways

import conf.Hosts
import cucumber.api.DataTable
import domains.stubby.StubbyResponse
import gherkin.deps.com.google.gson.Gson
import khttp.responses.Response
import parsers.DataTableParser

object StubbyGateway {
    private val gson = Gson()
    private val APIS = mapOf(
        "obter marcas de carros" to "/carros/marcas.json",
        "obter veículos de uma marca" to "/carros/veiculos/{brandId}.json",
        "obter modelos de um veículo" to "/carros/veiculo/{brandId}/{vehicleId}.json",
        "obter detalhes de um modelo" to "/carros/veiculo/{brandId}/{vehicleId}/{modelId}.json"
    )

    fun create(apiName: String, dataTable: DataTable): Response {
        val stubbyRequest =
            DataTableParser.parseMockRequestDataTable(
                APIS[apiName],
                "mocks/fipe/$apiName", dataTable)

        return khttp.request(
            method = "POST",
            url = Hosts.MOCKS_FIPE.address,
            json = gson.toJson(stubbyRequest)
        )
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

    fun getAllServices(): List<StubbyResponse> =
        gson.fromJson(khttp.request(
            method = "GET",
            url = Hosts.MOCKS_FIPE.address
        ).text, listOf<StubbyResponse>()::class.java)

    fun deleteAllServices() {
        getAllServices().forEach { service -> delete(service.id) }
    }
}
