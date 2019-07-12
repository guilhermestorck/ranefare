package gateways

import conf.Config
import cucumber.api.DataTable
import khttp.responses.Response
import parsers.DataTableParser

object AppGateway {

    fun request(serviceName: String, dataTable: DataTable): Response {
        val requestDataTable =
            DataTableParser.parseAppRequestDataTable(
                Config.MOCKED_API_NAMES.getHostUrl("app", serviceName),
                serviceName, dataTable)

        return khttp.request(
            method = requestDataTable.method,
            url = requestDataTable.url,
            headers = requestDataTable.headers,
            json = requestDataTable.body,
            params = requestDataTable.params
        )
    }
}