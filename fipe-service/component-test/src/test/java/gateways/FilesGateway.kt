package gateways

object FilesGateway {
    private const val BASE_FOLDER = "/json"

    fun getRequestString(resourcePath: String, resourceName: String): String {
        return getFileAsString("${getResourcePath(resourcePath)}/requests/$resourceName.json")
    }

    fun getResponseString(resourcePath: String, resourceName: String): String {
        return getFileAsString("${getResourcePath(resourcePath)}/responses/$resourceName.json")
    }

    private fun getResourcePath(resourcePath: String): String =
        "$BASE_FOLDER/${resourcePath.replace(' ', '-')}"

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