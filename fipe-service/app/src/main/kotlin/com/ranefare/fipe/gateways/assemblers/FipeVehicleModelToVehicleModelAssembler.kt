package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleModel
import com.ranefare.fipe.gateways.domains.FipeVehicleModelResponse
import javax.inject.Singleton

@Singleton
class FipeVehicleModelToVehicleModelAssembler {
    fun assemble(fipeVehicleModelResponse: FipeVehicleModelResponse, vehicle: Vehicle) = VehicleModel(
        id = fipeVehicleModelResponse.id,
        name = fipeVehicleModelResponse.name,
        vehicle = vehicle.copy(
            brand = vehicle.brand.copy(
                name = vehicle.brand.name ?: fipeVehicleModelResponse.fipe_marca
            )
        )
    )
}