package com.ranefare.quotation.contract.domains.getquotationplans

data class QuotationOptionResponse(
    val id: String,
    val name: String,
    val value: Double,
    val coverageItems: Map<String, Boolean>
)
