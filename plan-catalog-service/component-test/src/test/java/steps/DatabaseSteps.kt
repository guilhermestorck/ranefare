package steps

import cucumber.api.DataTable
import cucumber.api.java8.En
import gateways.DatabaseGateway
import junit.framework.Assert.assertEquals

class DatabaseSteps : En {

    init {

        Given("^that the database is empty$") {
            DatabaseGateway.cleanDatabase()
        }

        Then("^the \"([^\"]*)\" table contains (\\d+) records$") { tableName: String, count: Int ->
            assertEquals(DatabaseGateway.countRows(tableName), count)
        }

        Then("the \"([^\"]*)\" table contains the following rows:") { tableName: String, rows: DataTable ->
            rows.asMaps(String::class.java, String::class.java).forEach { row ->
                assertEquals(DatabaseGateway.containsRow(tableName, row), true)
            }
        }
    }

}
