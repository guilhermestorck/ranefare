package gateways

object FilesGateway {

    private const val BASE_FOLDER = "/json"

    fun getResponseString(apiName: String, responseName: String): String {
        return getFileAsString("$BASE_FOLDER/${apiName.replace(' ', '-')}/responses/$responseName.json")
    }

    fun getRequestString(apiName: String, requestName: String): String {
        return getFileAsString("$BASE_FOLDER/${apiName.replace(' ', '-')}/requests/$requestName.json")
    }

    fun getFileAsString(filePath: String): String {
        return this::class.java.getResource(filePath)
            .readText(Charsets.UTF_8)
    }
}