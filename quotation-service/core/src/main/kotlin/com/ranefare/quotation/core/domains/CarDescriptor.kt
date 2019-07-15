package com.ranefare.quotation.core.domains

data class CarDescriptor(
    val carBrand: Int,
    val carModel: String,
    val carYear: Int,
    val zeroKm: Boolean
)