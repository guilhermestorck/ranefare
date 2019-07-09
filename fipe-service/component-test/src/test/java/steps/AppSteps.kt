package steps

import cucumber.api.DataTable
import cucumber.api.java8.Pt
import gateways.AppGateway
import khttp.responses.Response
import org.junit.Assert.assertEquals
import org.skyscreamer.jsonassert.JSONAssert
import parsers.DataTableParser

class AppSteps : Pt {

    private val responses = mutableMapOf<String, Response>()

    init {

        Before { ->
            responses.clear()
        }

        Quando("^o serviço \"([^\"]*)\" da API deste módulo for chamado com os atributos:$")
        { apiName: String, dataTable: DataTable ->
            responses[apiName] = AppGateway.request(apiName, dataTable)
        }

        Então("^o serviço \"([^\"]*)\" da API deste módulo responde com os atributos:$")
        { apiName: String, dataTable: DataTable ->
            val responseDataTable = DataTableParser.parseAppResponseDataTable(apiName, dataTable)

            if (responseDataTable.body != null) {
                JSONAssert.assertEquals(responseDataTable.body, responses[apiName]?.text, true)
            }

            if (responseDataTable.status != null) {
                assertEquals(
                    "The \"$apiName\" API response status is ${responseDataTable.status}",
                    responseDataTable.status.toInt(),
                    responses[apiName]?.statusCode)
            }

            if (responseDataTable.headers != null) {
                assertEquals(
                    "The \"$apiName\" API response headers are ${responseDataTable.headers}",
                    responseDataTable.headers,
                    responses[apiName]?.headers)
            }
        }

    }
}