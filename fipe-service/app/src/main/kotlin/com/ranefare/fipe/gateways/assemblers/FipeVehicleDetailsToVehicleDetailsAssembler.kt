package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.VehicleDetails
import com.ranefare.fipe.core.domains.VehicleModel
import com.ranefare.fipe.gateways.domains.FipeVehicleDetailsResponse
import javax.inject.Singleton

@Singleton
class FipeVehicleDetailsToVehicleDetailsAssembler {
    fun assemble(fipeVehicleDetailsResponse: FipeVehicleDetailsResponse, vehicleModel: VehicleModel) = VehicleDetails(
        fuel = fipeVehicleDetailsResponse.combustivel,
        model = vehicleModel.copy(
            vehicle = vehicleModel.vehicle.copy(
                name = vehicleModel.vehicle.name ?: fipeVehicleDetailsResponse.name
            )
        ),
        price = fipeVehicleDetailsResponse.preco,
        year = fipeVehicleDetailsResponse.ano_modelo
    )
}