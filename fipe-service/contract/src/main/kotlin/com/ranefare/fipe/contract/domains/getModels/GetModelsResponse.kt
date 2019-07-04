package com.ranefare.fipe.contract.domains.getModels

data class GetModelsResponse(
    val brandId: Int,
    val models: List<ModelResponse>
)