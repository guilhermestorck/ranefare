package com.ranefare.quotation.core.domains

data class QuotationOption(
    val id: String,
    val name: String,
    val value: Double,
    val coverageItems: Map<String, Boolean>
)