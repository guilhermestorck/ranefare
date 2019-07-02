package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.VehicleDetails
import com.ranefare.fipe.core.domains.VehicleModel
import com.ranefare.fipe.gateways.domains.FipeVehicleDetails
import javax.inject.Singleton

@Singleton
class FipeVehicleDetailsToVehicleDetailsAssembler {
    fun assemble(fipeVehicleDetails: FipeVehicleDetails, vehicleModel: VehicleModel): VehicleDetails {
        return VehicleDetails(
                fuel = fipeVehicleDetails.combustivel,
                model = vehicleModel.copy(
                        vehicle = vehicleModel.vehicle.copy(
                                name = vehicleModel.vehicle.name ?: fipeVehicleDetails.name
                        )
                ),
                price = fipeVehicleDetails.preco,
                year = fipeVehicleDetails.ano_modelo
        )
    }
}