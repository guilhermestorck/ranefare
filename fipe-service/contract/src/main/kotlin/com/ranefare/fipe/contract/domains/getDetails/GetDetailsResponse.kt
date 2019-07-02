package com.ranefare.fipe.contract.domains.getDetails

data class GetDetailsResponse(
        val brandId: Int,
        val brandName: String,
        val fuel: String,
        val modelId: String,
        val modelName: String,
        val price: String,
        val year: String
)