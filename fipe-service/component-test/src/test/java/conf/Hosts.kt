package conf

enum class Hosts(val address: String) {
    APP(Config.getEnv("gateways_app_endpoint", "")),
    MOCKS_FIPE(Config.getEnv("gateways_mocks_fipe_endpoint", ""))
}