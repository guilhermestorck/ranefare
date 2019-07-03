package com.ranefare.fipe.gateways.domains

data class FipeVehicleDetailsResponse(
    val ano_modelo: String,
    val combustivel: String,
    val fipe_codigo: String?,
    val id: String?,
    val key: String?,
    val marca: String?,
    val name: String,
    val preco: String,
    val referencia: String?,
    val time: Double?,
    val veiculo: String?
)