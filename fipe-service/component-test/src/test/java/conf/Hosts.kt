package conf

enum class Hosts(val address: String) {
    APP(Config.getEnv("gateways_app_endpoint", "http://localhost:8080")),
    MOCKS_FIPE(Config.getEnv("gateways_mocks_fipe_endpoint", "http://localhost:8901"))
}