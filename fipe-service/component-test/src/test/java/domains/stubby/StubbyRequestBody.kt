package domains.stubby

data class StubbyRequestBody(
    val method: String,
    val url: String,
    val json: String?,
    val post: String?,
    val headers: Map<String, String>,
    val query: Map<String, String>
)