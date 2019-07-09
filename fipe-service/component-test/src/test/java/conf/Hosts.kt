package conf

enum class Hosts(val address: String) {
    APP(Config.getEnv("gateways.app.endpoint", "")),
    MOCKS_FIPE(Config.getEnv("gateways.mocks.fipe.endpoint", ""))
}