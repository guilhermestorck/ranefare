package conf

enum class Hosts {

    APP {
        override fun address() = Config.getEnv("app_url", default = "http://localhost:8080")
    },
    MOCKS {
        override fun address() = Config.getEnv("mocks_url", default = "http://mocks:8081")
    };

    abstract fun address(): String
}