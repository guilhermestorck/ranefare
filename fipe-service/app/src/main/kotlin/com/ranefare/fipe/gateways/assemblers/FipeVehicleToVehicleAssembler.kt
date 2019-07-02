package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.gateways.domains.FipeVehicleResponse
import javax.inject.Singleton

@Singleton
class FipeVehicleToVehicleAssembler {
    fun assemble(fipeVehicleResponse: FipeVehicleResponse, vehicleBrand: VehicleBrand) = Vehicle(
        brand = vehicleBrand,
        id = fipeVehicleResponse.id,
        name = fipeVehicleResponse.name
    )
}