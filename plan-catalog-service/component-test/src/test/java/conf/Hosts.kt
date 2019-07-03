package  conf

enum class Hosts {

    APP {
        override fun address() = Config.getEnv("app_url", default = "http://localhost:8082")
    };

    abstract fun address(): String
}