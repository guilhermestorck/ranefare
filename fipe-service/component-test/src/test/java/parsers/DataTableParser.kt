package parsers

import cucumber.api.DataTable
import domains.datatable.IntegrationMockTimes
import domains.datatable.RequestDataTable
import domains.datatable.ResponseDataTable
import domains.stubby.StubbyRequest
import domains.stubby.StubbyRequestBody
import domains.stubby.StubbyResponseBody
import gateways.FilesGateway
import org.json.JSONTokener

object DataTableParser {
    private const val METHOD = "method"
    private const val PATH_PARAM = "pathParam"
    private const val QUERY_PARAM = "queryParam"
    private const val HEADER = "header"
    private const val BODY = "body"
    private const val STATUS = "status"

    private const val REQUEST = "request"
    private const val RESPONSE = "response"

    fun parseAppRequestDataTable(baseUrl: String?, serviceName: String, dataTable: DataTable): RequestDataTable {
        val groupedData = getGroupedData(dataTable)

        val method = parseSingleValue(METHOD, groupedData).orEmpty()
        val jsonBody = getJsonRequestBody("app/$serviceName", groupedData)

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

    fun parseAppResponseDataTable(serviceName: String, dataTable: DataTable): ResponseDataTable {
        val groupedData = getGroupedData(dataTable)

        return ResponseDataTable(
            status = parseSingleValue(STATUS, groupedData)?.toInt(),
            body = getStringResponseBody("app/$serviceName", groupedData),
            headers = parseMappedValues(HEADER, groupedData)
        )
    }

    fun parseMockStubbyRequest(baseUrl: String?, integrationName: String, serviceName: String, dataTable: DataTable): StubbyRequest {
        val groupedData = getGroupedData(dataTable)
        val requestMap = parseMappedValues(groupedData[REQUEST] ?: listOf())[0]
        val response = parseMappedValues(groupedData[RESPONSE] ?: listOf())

        val method = requestMap[METHOD].orEmpty().trim()
        val stringJsonBody = getStringRequestBody("mocks/$integrationName/$serviceName", requestMap[BODY].orEmpty().trim())

        if (method == "GET" && stringJsonBody != null)
            throw UnsupportedOperationException("Um request com método 'GET' não pode ter 'body'.")

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
                    body = getStringResponseBody("mocks/$integrationName/$serviceName", mapResponse[BODY].orEmpty().trim()),
                    status = mapResponse[STATUS]?.trim()?.toInt()
                )
            }
        )
    }

    fun parseIntegrationMockTimes(dataTable: DataTable): List<IntegrationMockTimes> =
        dataTable.asLists(String::class.java)
            .drop(1)
            .map { row ->
                if (row.size != 3) throw UnsupportedOperationException(
                    "Formato inválido para IntegrationMockTimesDataTable na linha a seguir: '$row'. " +
                        "Um IntegrationMockTimesDataTable válido deve seguir o seguinte formato: " +
                        "'| Nome da integração | Nome do serviço | Acionamentos no mock |'.")
                IntegrationMockTimes(
                    integrationName = row[0],
                    serviceName = row[1],
                    times = row[2].toInt()
                )
            }.toList()

    private fun getGroupedData(dataTable: DataTable): Map<String, List<List<String>>> =
        dataTable.asLists(String::class.java).groupBy { it[0] }

    private fun getUrl(baseUrl: String?, requestData: Map<String, List<List<String>>>): String =
        getUrlWithPathParams(baseUrl, getPathParams(requestData))

    private fun getUrlWithPathParams(baseUrl: String?, pathParams: Map<String, String>): String =
        pathParams.entries.fold(baseUrl!!) { url, pathParam ->
            url.replace("{${pathParam.key}}", pathParam.value)
        }

    private fun getPathParams(requestData: Map<String, List<List<String>>>): Map<String, String> =
        parseMappedValues(PATH_PARAM, requestData) ?: mapOf()

    private fun getJsonRequestBody(resourcePath: String, requestData: Map<String, List<List<String>>>): Any? =
        getJsonRequestBody(resourcePath, parseSingleValue(BODY, requestData).orEmpty())

    private fun getJsonRequestBody(resourcePath: String, bodyLabel: String): Any? =
        if (bodyLabel.isBlank()) null
        else JSONTokener(FilesGateway.getRequestString(resourcePath, bodyLabel)).nextValue()

    private fun getStringResponseBody(resourcePath: String, requestData: Map<String, List<List<String>>>): String? =
        getStringResponseBody(resourcePath, parseSingleValue(BODY, requestData).orEmpty())

    private fun getStringRequestBody(resourcePath: String, bodyLabel: String): String? =
        if (bodyLabel.isBlank()) {
            null
        } else {
            FilesGateway.getRequestString(resourcePath, bodyLabel)
        }

    private fun getStringResponseBody(resourcePath: String, bodyLabel: String): String? =
        if (bodyLabel.isBlank()) {
            null
        } else {
            FilesGateway.getResponseString(resourcePath, bodyLabel)
        }

    private fun parseSingleValue(key: String, requestData: Map<String, List<List<String>>>): String? =
        requestData[key]?.get(0)?.get(1)?.trim()

    private fun parseMappedValues(
        key: String, requestData: Map<String, List<List<String>>>
    ): Map<String, String>? =
        requestData[key]?.map {
            val parts = it[1].split(":")
            if (parts.size != 2)
                throw UnsupportedOperationException(
                    "Formato inválido para valor mapeado: '${it[1]}'. Um valor mapeado válido deve estar no seguinte formato: 'key: value'")

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

    private fun parseMappedValues(mappedValues: String?): Map<String, String> {
        val itemTableValue = mappedValues.orEmpty().trim()

        if (itemTableValue.isBlank()) return mapOf()

        if (!itemTableValue.startsWith("[") || !itemTableValue.endsWith("]"))
            throw UnsupportedOperationException(
                "Formato inválido para mapa de valores: '$itemTableValue'. " +
                    "Um mapa de valores válido deve estar no seguinte formato: '[key1: value1, key2: value2, ... , keyN: valueN]'.")

        return itemTableValue.removePrefix("[")
            .removeSuffix("]")
            .split(",").map { pair: String ->
                val separatedPair = pair.trim().split(":")
                if (separatedPair.size != 2)
                    throw UnsupportedOperationException(
                        "Formato inválido para valor mapeado: '$separatedPair'. " +
                            "Um valor mapeado válido deve estar no seguinte formato: 'key: value'")

                separatedPair[0].trim() to separatedPair[1].trim()
            }.toMap()
    }
}