package com.ranefare.fipe.core.usecase

import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.core.gateways.FipeGateway

class ObtainVehicleBrands(val fipeGateway: FipeGateway) {
    fun execute(vehicleType: VehicleType): List<VehicleBrand> {
        return fipeGateway.getBrands(vehicleType)
    }
}