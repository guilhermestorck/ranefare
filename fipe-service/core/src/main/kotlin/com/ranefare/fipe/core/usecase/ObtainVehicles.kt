package com.ranefare.fipe.core.usecase

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.gateways.FipeGateway

class ObtainVehicles(val fipeGateway: FipeGateway) {
    fun execute(vehicleBrand: VehicleBrand): List<Vehicle> {
        return fipeGateway.getVehicles(vehicleBrand)
    }
}