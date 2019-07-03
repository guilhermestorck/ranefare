package com.ranefare.fipe.controllers.converters

import com.ranefare.fipe.contract.domains.getDetails.GetDetailsResponse
import com.ranefare.fipe.core.domains.VehicleDetails
import javax.inject.Singleton

@Singleton
class VehicleDetailsToGetDetailsResponseConverter {
    fun convert(details: VehicleDetails): GetDetailsResponse = GetDetailsResponse(
        brandId = details.model.vehicle.brand.id,
        brandName = details.model.vehicle.brand.name!!,
        fuel = details.fuel,
        modelId = details.model.vehicle.id,
        modelName = details.model.vehicle.name!!,
        price = details.price,
        year = details.year
    )
}