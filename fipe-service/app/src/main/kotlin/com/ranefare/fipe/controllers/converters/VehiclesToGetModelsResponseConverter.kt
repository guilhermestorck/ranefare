package com.ranefare.fipe.controllers.converters

import com.ranefare.fipe.contract.domains.getModels.GetModelsResponse
import com.ranefare.fipe.contract.domains.getModels.ModelResponse
import com.ranefare.fipe.core.domains.Vehicle
import javax.inject.Singleton

@Singleton
class VehiclesToGetModelsResponseConverter {
    fun convert(models: List<Vehicle>): GetModelsResponse = GetModelsResponse(
        models = models.map { vehicle ->
            ModelResponse(
                modelId = vehicle.id,
                modelName = vehicle.name!!,
                brandId = vehicle.brand.id
            )
        }
    )
}