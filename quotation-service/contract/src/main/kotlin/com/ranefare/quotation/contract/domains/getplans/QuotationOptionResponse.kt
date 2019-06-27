package com.ranefare.quotation.contract.domains.getplans

data class QuotationOptionResponse(
    val name: String,
    val price: Double,
    val coverageItems: Map<String, Boolean>
)