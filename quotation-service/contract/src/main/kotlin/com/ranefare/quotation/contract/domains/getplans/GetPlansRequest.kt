package com.ranefare.quotation.contract.domains.getplans

data class GetPlansRequest(
    val carBrand: String,
    val carModel: String,
    val carYear: Int,
    val zeroKm: Boolean,
    val cpf: String
)