package com.ranefare.quotation.core.domains

data class InsurancePlan(
    val id: String,
    val name: String,
    val costRate: Double,
    val coverageItems: List<InsuranceCoverageItem>
)