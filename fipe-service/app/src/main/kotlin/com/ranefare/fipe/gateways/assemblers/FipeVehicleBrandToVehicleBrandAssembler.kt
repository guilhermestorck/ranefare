package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.gateways.domains.FipeVehicleBrandResponse
import javax.inject.Singleton

@Singleton
class FipeVehicleBrandToVehicleBrandAssembler {
    fun assemble(fipeVehicleBrandResponse: FipeVehicleBrandResponse, vehicleType: VehicleType) = VehicleBrand(
        id = fipeVehicleBrandResponse.id,
        name = fipeVehicleBrandResponse.name,
        vehicleType = vehicleType
    )
}