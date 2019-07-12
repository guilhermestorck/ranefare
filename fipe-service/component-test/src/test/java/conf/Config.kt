package conf

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import domains.MockedApiNames

object Config {
    val MOCKED_API_NAMES: MockedApiNames = loadMockedApiNames()

    fun loadMockedApiNames(): MockedApiNames {
        val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
        mapper.registerModule(KotlinModule()) // Enable Kotlin support

        val inputStream = this.javaClass
            .classLoader
            .getResourceAsStream("config/mockedApiNames.yml")

        return inputStream.use {
            mapper.readValue(it, MockedApiNames::class.java)
        }
    }

    fun getEnv(propName: String, default: String): String {
        val env = System.getenv(propName)
        return if (env.isNullOrBlank() || env == "null") default else env
    }
}