package steps

import conf.DatabaseTable
import cucumber.api.DataTable
import cucumber.api.java8.En
import gateways.DatabaseGateway
import junit.framework.Assert.assertEquals

class DatabaseSteps : En {

    init {

        Given("^that the database is empty$") {
            DatabaseGateway.cleanDatabase()
        }

        Given("^the \"([^\"]*)\" table has the following rows:$") { table: DatabaseTable, rows: DataTable ->
            rows.asMaps(String::class.java, String::class.java).forEach { row ->
                DatabaseGateway.insertRow(table.tableName, row)
            }
        }

        Then("^the \"([^\"]*)\" table contains (\\d+) rows$") { table: DatabaseTable, count: Int ->
            val rowCount = DatabaseGateway.countRows(table.tableName)
            val assertErrorMessage = "The table ${table.tableName} contains $rowCount rows"

            assertEquals(assertErrorMessage, rowCount, count)
        }

        Then("the \"([^\"]*)\" table contains the following rows:") { table: DatabaseTable, rows: DataTable ->
            rows.asMaps(String::class.java, String::class.java).forEach { row ->
                val assertErrorMessage = "The row $row was not found on table \"${table.tableName}\""

                assertEquals(assertErrorMessage, DatabaseGateway.containsRow(table.tableName, row), true)
            }
        }

    }

}
