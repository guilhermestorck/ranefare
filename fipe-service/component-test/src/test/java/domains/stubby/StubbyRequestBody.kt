package domains.stubby

data class StubbyRequestBody(
    val url: String,
    val method: String,
    val post: Any,
    val json: Any,
    val headers: Map<String, String>,
    val query: Map<String, String>
)