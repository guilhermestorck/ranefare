package gateways

import conf.Hosts
import cucumber.api.DataTable
import khttp.responses.Response
import parsers.DataTableParser

object AppGateway {

    private val APIS = mapOf(
        "obter marcas de carros" to "/fipe/brands",
        "obtém modelos de uma marca" to "/fipe/brands/{brandId}/models",
        "obtém detalhes de um modelo" to "/fipe/brands/{brandId}/models/{modelId}/details"
    )

    fun request(apiName: String, dataTable: DataTable): Response {
        val requestDataTable =
            DataTableParser.parseAppRequestDataTable(
                "${Hosts.APP.address}${APIS[apiName]}",
                "app/$apiName", dataTable)

        return khttp.request(
            method = requestDataTable.method,
            url = requestDataTable.url,
            headers = requestDataTable.headers,
            json = requestDataTable.body,
            params = requestDataTable.params
        )
    }
}