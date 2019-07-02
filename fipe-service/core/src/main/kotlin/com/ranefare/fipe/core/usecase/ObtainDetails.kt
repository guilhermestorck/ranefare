package com.ranefare.fipe.core.usecase

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleDetails
import com.ranefare.fipe.core.gateways.FipeGateway

class ObtainDetails(val fipeGateway: FipeGateway) {
    fun execute(vehicle: Vehicle): VehicleDetails {
        return fipeGateway.getVehiclePrice(fipeGateway.getVehicleModels(vehicle).first())
    }
}