package steps

import cucumber.api.DataTable
import cucumber.api.java8.Pt
import gateways.StubbyGateway

class MockSteps : Pt {

    private val mocks = mutableMapOf<String, Int>()

    init {

        Before { ->
            mocks.clear()
        }

        Dado("um mock no serviço \"([^\"]*)\" da integração \"([^\"]*)\" com requisição e resposta com os atributos:$")
        { apiName: String, integrationName: String, dataTable: DataTable ->
            mocks[apiName] = StubbyGateway.create(apiName, integrationName, dataTable)
        }

    }
}