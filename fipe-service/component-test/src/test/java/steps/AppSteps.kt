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
        { serviceName: String, dataTable: DataTable ->
            responses[serviceName] = AppGateway.request(serviceName, dataTable)
        }

        Então("^o serviço \"([^\"]*)\" da API deste módulo responde com os atributos:$")
        { serviceName: String, dataTable: DataTable ->
            val responseDataTable = DataTableParser.parseAppResponseDataTable(serviceName, dataTable)

            if (responseDataTable.body != null) {
                JSONAssert.assertEquals(responseDataTable.body, responses[serviceName]?.text, true)
            }

            if (responseDataTable.status != null) {
                assertEquals(
                    "The \"$serviceName\" API response status is ${responseDataTable.status}",
                    responseDataTable.status.toInt(),
                    responses[serviceName]?.statusCode)
            }

            if (responseDataTable.headers != null) {
                assertEquals(
                    "The \"$serviceName\" API response headers are ${responseDataTable.headers}",
                    responseDataTable.headers,
                    responses[serviceName]?.headers)
            }
        }

    }
}