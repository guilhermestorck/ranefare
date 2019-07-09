package steps

import cucumber.api.DataTable
import cucumber.api.java8.Pt
import gateways.StubbyGateway

class MockFipeSteps : Pt {

    private val mocks = mutableMapOf<String, Int>()

    init {

        Before { ->
            mocks.clear()
        }

        Dado("um mock no serviço \"([^\"]*)\" da API FIPE com requisição e resposta com os atributos:$")
        { apiName: String, dataTable: DataTable ->
            mocks[apiName] = StubbyGateway.create(apiName, dataTable)
        }

    }
}