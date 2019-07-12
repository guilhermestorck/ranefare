package steps

import cucumber.api.DataTable
import cucumber.api.java8.Pt
import gateways.StubbyGateway
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import parsers.DataTableParser

class MockSteps : Pt {

    private val mocks = mutableMapOf<String, MutableMap<String, Int>>()

    init {

        Before { ->
            mocks.clear()
        }

        Dado("^um mock no serviço \"([^\"]*)\" da integração \"([^\"]*)\" com requisição e resposta com os atributos:$")
        { serviceName: String, integrationName: String, dataTable: DataTable ->
            if (mocks[integrationName] == null) mocks[integrationName] = mutableMapOf<String, Int>()
            mocks[integrationName]!![serviceName] = StubbyGateway.create(serviceName, integrationName, dataTable)
        }

        E("^apenas os mocks de integrações abaixo foram chamados:$")
        { dataTable: DataTable ->
            DataTableParser.parseIntegrationMockTimes(dataTable)
                .groupBy { times -> times.integrationName }.toMap()
                .forEach { timesEntry ->
                    val integrationName = timesEntry.key
                    val timesList = timesEntry.value

                    val stubbyHits = StubbyGateway.getAllServices(integrationName).map { response ->
                        response.id to response.hits
                    }.toMap()

                    timesList.forEach { times ->
                        val stubbyId = mocks[times.integrationName]?.get(times.serviceName)
                        if (stubbyId == null) {
                            fail("Não existe nenhum mock no Stubby para o serviço '${times.serviceName}' " +
                                "da integração '${times.integrationName}'.")
                        }
                        assertEquals(
                            "O mock do serviço '${times.serviceName}' da integração '${times.integrationName}' " +
                                "foi acionado ${stubbyHits[stubbyId]} vez(es), mas devia ter sido acionado ${times.times} vez(es).",
                            stubbyHits[stubbyId],
                            times.times)
                    }

                    stubbyHits.forEach { hitsEntry ->
                        val timesRowsMatched = timesList.filter { times ->
                            val stubbyId = mocks[times.integrationName]?.get(times.serviceName) ?: -1
                            stubbyId == hitsEntry.key
                        }
                        if (timesRowsMatched.isEmpty()) {
                            fail("Existe um mock no Stubby com id " +
                                "${hitsEntry.key} que foi acionado ${hitsEntry.value} vez(es), " +
                                "mas isso não está descrito na tabela de validação de acionamentos dos mocks.")
                        }

                        val timesRowMatched = timesRowsMatched.first()
                        if (timesRowsMatched.size != 1)
                            fail("Existe ${timesRowsMatched.size} linhas de validação para o serviço " +
                                "\"${timesRowMatched.serviceName}\" da integração " +
                                "\"${timesRowMatched.integrationName}\". Revise sua tabela de validação de acionamentos dos mocks.")

                        assertEquals(
                            "O mock do serviço '${timesRowMatched.serviceName}' da integração '${timesRowMatched.integrationName}' " +
                                "foi acionado ${hitsEntry.value} vez(es), mas devia ter sido acionado ${timesRowMatched.times} vez(es).",
                            hitsEntry.value, timesRowMatched.times)
                    }
                }
        }
    }

}