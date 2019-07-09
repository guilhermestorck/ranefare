package domains.stubby

data class StubbyResponseBody(
    val headers: Map<String, String>,
    val status: Int?,
    val body: Any?
)