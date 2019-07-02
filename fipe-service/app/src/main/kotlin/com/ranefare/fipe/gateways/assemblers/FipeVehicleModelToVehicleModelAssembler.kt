package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleModel
import com.ranefare.fipe.gateways.domains.FipeVehicleModel
import javax.inject.Singleton

@Singleton
class FipeVehicleModelToVehicleModelAssembler {
    fun assemble(fipeVehicleModel: FipeVehicleModel, vehicle: Vehicle): VehicleModel {
        return VehicleModel(
                id = fipeVehicleModel.id,
                name = fipeVehicleModel.name,
                vehicle = vehicle.copy(
                        brand = vehicle.brand.copy(
                                name = vehicle.brand.name ?: fipeVehicleModel.marca
                        )
                )
        )
    }
}