package steps

import cucumber.api.DataTable
import cucumber.api.java8.Pt
import gateways.StubbyGateway
import khttp.responses.Response

class MockFipeSteps : Pt {

    private val responses = mutableMapOf<String, Response>()

    init {

        Before { ->
            responses.clear()
        }

        Dado("um mock no serviço \"([^\"]*)\" da API FIPE com requisição e resposta com os atributos:$")
        { apiName: String, dataTable: DataTable ->
            responses[apiName] = StubbyGateway.create(apiName, dataTable)
        }

    }
}