package steps

import conf.Hosts
import cucumber.api.DataTable
import cucumber.api.java8.En
import gateways.FilesGateway
import gherkin.deps.com.google.gson.Gson
import khttp.request
import khttp.responses.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.collections.set

class ApiSteps : En {

    private val APIS = mapOf(
        "create insurance plan" to "/insurance/plans"
    )

    init {

        val gson = Gson()
        
        val responses = mutableMapOf<String, Response>()

        When("^the \"([^\"]*)\" API is called with:$") { apiName: String, datatable: DataTable ->
            val requestData = datatable.asLists(String::class.java).groupBy { it[0] }

            val url = "${Hosts.APP.address()}${APIS[apiName]}"
            val method = requestData["method"]?.get(0)?.get(1) ?: ""
            val body = requestData["body"]?.get(0)?.get(1) ?: ""
            val headers = requestData["header"]?.map {
                val parts = it[1].split(":", limit = 2)
                parts[0].trim() to parts[1].trim()
            }?.toMap() ?: mapOf()
            val params = requestData["param"]?.map {
                val parts = it[1].split(":", limit = 2)
                parts[0].trim() to parts[1].trim()
            }?.toMap() ?: mapOf()

            responses[apiName] = request(
                method = method,
                url = url,
                headers = headers,
                json = gson.fromJson(FilesGateway.getRequestString(apiName, body), Map::class.java),
                params = params
            )
        }

        Then("^the \"([^\"]*)\" API response has:$") { apiName: String, datatable: DataTable ->
            val responseData = datatable.asLists(String::class.java).groupBy { it[0] }

            val status = responseData["method"]?.get(0)?.get(1)
            val body = responseData["body"]?.get(0)?.get(1)
            val headers = responseData["header"]?.map {
                val parts = it[1].split(":", limit = 2)
                parts[0].trim() to parts[1].trim()
            }?.toMap()

            if (body != null) {
                val expectedBody = FilesGateway.getResponseString(apiName, body)
                JSONAssert.assertEquals(expectedBody, responses[apiName]?.text, false)
            }

            if (status != null) {
                assertEquals(status.toInt(), responses[apiName]?.statusCode)
            }

            if (headers != null) {
                assertEquals(headers, responses[apiName]?.headers)
            }

        }


//    var response: Response? = null
//
//    @When("^I call the GetProductById API with params:$")
//    fun whenICallTheApiWithParams(dataTable: DataTable) {
//        val params = dataTable.asMap(String::class.java, String::class.java)
//        response = get("${Hosts.CATALOG.address()}/catalog/products/${params.get("id")}")
//    }
//
//    @Then("^the GetProductById API response status should be (\\d+)$")
//    fun thenTheApiResponseStatusShouldBe(status: Int) {
//        assert(response?.statusCode == status)
//    }
//
//    @Then("^the GetProductById API response should have a body equals to \"([^\"]*)\"$")
//    fun theTheApiResponseShouldHaveBodyEqualsTo(responseName: String) {
//        val actual = response?.jsonObject
//        val expected = FilesGateway.getResponseString("get-product-by-id", responseName)
//
//        JSONAssert.assertEquals(expected, actual, false)
//    }
    }
}