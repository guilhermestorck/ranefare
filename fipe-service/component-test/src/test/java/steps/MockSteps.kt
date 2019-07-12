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
            val formattedIntegrationName = integrationName.replace(' ', '-')
            val formattedServiceName = serviceName.replace(' ', '-')

            if (mocks[formattedIntegrationName] == null) mocks[formattedIntegrationName] = mutableMapOf<String, Int>()
            mocks[formattedIntegrationName]!![formattedServiceName] = StubbyGateway.create(formattedServiceName, formattedIntegrationName, dataTable)
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
                            fail("There isn't any stubby for " +
                                "\"${times.serviceName}\" service of " +
                                "\"${times.integrationName}\" integration.")
                        }
                        assertEquals(
                            "The stubby hits of " +
                                "\"${times.serviceName}\" service of " +
                                "\"${times.integrationName}\" integration is ${stubbyHits[stubbyId]}, " +
                                "but should be ${times.times}.",
                            stubbyHits[stubbyId],
                            times.times)
                    }

                    stubbyHits.forEach { hitsEntry ->
                        val timesRowsMatched = timesList.filter { times ->
                            val stubbyId = mocks[times.integrationName]?.get(times.serviceName) ?: -1
                            stubbyId == hitsEntry.key
                        }
                        if (timesRowsMatched.isEmpty()) {
                            fail("There is a stubby with id " +
                                "${hitsEntry.key} that was hit ${hitsEntry.value} times, " +
                                "but there isn't a table test for its.")
                        }

                        val timesRowMatched = timesRowsMatched.first()
                        if (timesRowsMatched.size != 1)
                            fail("There are ${timesRowsMatched.size} assert rows for " +
                                "\"${timesRowMatched.serviceName}\" service of " +
                                "\"${timesRowMatched.integrationName}\" integration.")

                        assertEquals(
                            "There is a stubby with id " +
                                "${hitsEntry.key} that was hit ${hitsEntry.value} times, " +
                                "but the times in table test is ${timesRowMatched.times}.", hitsEntry.value, timesRowMatched.times)
                    }
                }
        }
    }

}