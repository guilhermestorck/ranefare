package parsers

import cucumber.api.DataTable
import domains.datatable.RequestDataTable
import domains.datatable.ResponseDataTable
import domains.stubby.StubbyRequest
import domains.stubby.StubbyRequestBody
import domains.stubby.StubbyResponseBody
import gateways.FilesGateway
import gherkin.deps.com.google.gson.Gson

object DataTableParser {
    private const val METHOD = "method"
    private const val PATH_PARAM = "pathParam"
    private const val QUERY_PARAM = "queryParam"
    private const val HEADER = "header"
    private const val BODY = "body"
    private const val STATUS = "status"

    private const val REQUEST = "request"
    private const val RESPONSE = "response"

    private val gson = Gson()

    fun parseAppRequestDataTable(baseUrl: String?, apiName: String, dataTable: DataTable): RequestDataTable {
        val groupedData = getGroupedData(dataTable)

        val method = parseSingleValue(METHOD, groupedData).orEmpty()
        val jsonBody = getJsonRequestBody(apiName, groupedData)

        if (method == "GET" && jsonBody != null)
            throw UnsupportedOperationException("A GET request can't have a body.")

        return RequestDataTable(
            method = method,
            url = getUrl(baseUrl, groupedData),
            body = jsonBody,
            headers = parseMappedValues(HEADER, groupedData) ?: mapOf(),
            params = parseMappedValues(QUERY_PARAM, groupedData) ?: mapOf()
        )
    }

    fun parseAppResponseDataTable(apiName: String, dataTable: DataTable): ResponseDataTable {
        val groupedData = getGroupedData(dataTable)

        return ResponseDataTable(
            status = parseSingleValue(STATUS, groupedData)?.toInt(),
            body = getStringResponseBody(apiName, groupedData),
            headers = parseMappedValues(HEADER, groupedData)
        )
    }

    fun parseMockRequestDataTable(baseUrl: String?, apiName: String, dataTable: DataTable): StubbyRequest {
        val groupedData = getGroupedData(dataTable)
        val requestMap = parseMappedValues(groupedData[REQUEST] ?: mapOf())[0]
        val response = parseMappedValues(groupedData[RESPONSE] ?: mapOf())

        val method = requestMap[METHOD].orEmpty().trim()
        val stringJsonBody = getStringResponseBody(apiName, requestMap[BODY].orEmpty().trim())

        if (method == "GET" && stringJsonBody != null)
            throw UnsupportedOperationException("A GET request can't have a body.")

        return StubbyRequest(
            request = StubbyRequestBody(
                method = method,
                url = getUrlWithPathParams(baseUrl, parseMappedValues(requestMap[PATH_PARAM])),
                query = parseMappedValues(requestMap[QUERY_PARAM]),
                headers = parseMappedValues(requestMap[HEADER]),
                post = null,
                json = stringJsonBody
            ),
            response = response.map { mapResponse ->
                StubbyResponseBody(
                    headers = parseMappedValues(mapResponse[HEADER]),
                    body = getJsonRequestBody(apiName, mapResponse[BODY].orEmpty()),
                    status = mapResponse[STATUS]?.toInt()
                )
            }
        )
    }

    private fun getGroupedData(dataTable: DataTable): Map<String, List<MutableList<String>>> =
        dataTable.asLists(String::class.java).groupBy { it[0] }

    private fun getUrl(baseUrl: String?, requestData: Map<String, List<MutableList<String>>>): String =
        getUrlWithPathParams(baseUrl, getPathParams(requestData))

    private fun getUrlWithPathParams(baseUrl: String?, pathParams: Map<String, String>): String =
        pathParams.entries.fold(baseUrl!!) { url, pathParam ->
            url.replace("{${pathParam.key}}", pathParam.value)
        }

    private fun getPathParams(requestData: Map<String, List<MutableList<String>>>): Map<String, String> =
        parseMappedValues(PATH_PARAM, requestData) ?: mapOf()

    private fun getJsonRequestBody(apiName: String, requestData: Map<String, List<MutableList<String>>>): Any? =
        getJsonRequestBody(apiName, parseSingleValue(BODY, requestData).orEmpty())

    private fun getJsonRequestBody(apiName: String, bodyLabel: String): Any? =
        if (bodyLabel.isBlank()) {
            null
        } else {
            gson.fromJson(FilesGateway.getRequestString(apiName, bodyLabel), Map::class.java)
        }

    private fun getStringResponseBody(apiName: String, requestData: Map<String, List<MutableList<String>>>): String? =
        getStringResponseBody(apiName, parseSingleValue(BODY, requestData).orEmpty())

    private fun getStringResponseBody(apiName: String, bodyLabel: String): String? =
        if (bodyLabel.isBlank()) {
            null
        } else {
            FilesGateway.getResponseString(apiName, bodyLabel)
        }

    private fun parseSingleValue(key: String, requestData: Map<String, List<MutableList<String>>>): String? =
        requestData[key]?.get(0)?.get(1)?.trim()

    private fun parseMappedValues(
        key: String, requestData: Map<String, List<MutableList<String>>>
    ): Map<String, String>? =
        requestData[key]?.map {
            val parts = it[1].split(":")
            if (parts.size != 2)
                throw UnsupportedOperationException(
                    "Not valid EntryMap value: ${it[1]}. An EntryMap value must be in the format \"key: value\"")

            parts[0].trim() to parts[1].trim()
        }?.toMap()

    private fun parseMappedValues(linesTableData: List<List<String>>): List<Map<String, String>> =
        linesTableData.map { lineTableData ->
            parseMappedValues(lineTableData)
        }

    private fun parseMappedValues(lineTableData: List<String>): Map<String, String> =
        lineTableData.drop(1).map { mappedValue ->
            val parts = mappedValue.split(":")
            val key = parts[0]
            val value = parts.drop(1).joinToString(separator = ":")
            key to value
        }.toMap()

    private fun parseMappedValues(mappedValues: String?): Map<String, String> =
        mappedValues.orEmpty().trim()
            .removePrefix("[")
            .removeSuffix("]")
            .split(",").map { pair: String ->
                val separatedPair = pair.trim().split(":")
                if (separatedPair.size != 2)
                    throw UnsupportedOperationException(
                        "Not valid EntryMap value: $separatedPair. An EntryMap value must be in the format \"key: value\"")

                separatedPair[0].trim() to separatedPair[1].trim()
            }.toMap()
}