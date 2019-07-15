package com.ranefare.quotation.contract.domains.getquotationplans

data class GetQuotationOptionsRequest(
    val carBrand: Int,
    val carModel: String,
    val carYear: Int,
    val zeroKm: Boolean,
    val cpf: String
)
