package gateways

object FilesGateway {
    private const val BASE_FOLDER = "/json"

    fun getRequestString(apiName: String, resourceName: String): String {
        return getFileAsString("${getResourcePath(apiName)}/requests/$resourceName.json")
    }

    fun getResponseString(apiName: String, resourceName: String): String {
        return getFileAsString("${getResourcePath(apiName)}/responses/$resourceName.json")
    }

    private fun getResourcePath(resourceLabel: String): String =
        "$BASE_FOLDER/${resourceLabel.replace(' ', '-')}"

    private fun getFileAsString(filePath: String): String {
        try {
            return this::class.java.getResource(filePath)
                .readText(Charsets.UTF_8)
        } catch (e: Exception) {
            print("File $filePath was not found")
            throw e
        }
    }
}