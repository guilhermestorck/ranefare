package com.ranefare.fipe.controllers.converters

import com.ranefare.fipe.contract.domains.getModels.GetModelsResponse
import com.ranefare.fipe.contract.domains.getModels.ModelResponse
import com.ranefare.fipe.core.domains.Vehicle
import javax.inject.Singleton

@Singleton
class VehiclesToGetModelsResponseConverter {
    fun assemble(models: List<Vehicle>): GetModelsResponse {
        return GetModelsResponse(
            models = models.map { vehicle ->
                ModelResponse(
                    modelId = vehicle.id,
                    modelName = vehicle.name!!,
                    brandId = vehicle.brand.id
                )
            }
        )
    }
}