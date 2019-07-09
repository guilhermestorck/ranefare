package domains.stubby

data class StubbyRequest(
    val request: StubbyRequestBody,
    val response: List<StubbyResponseBody>
)