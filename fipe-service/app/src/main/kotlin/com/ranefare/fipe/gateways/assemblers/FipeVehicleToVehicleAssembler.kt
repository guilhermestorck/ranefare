package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.gateways.domains.FipeVehicle
import javax.inject.Singleton

@Singleton
class FipeVehicleToVehicleAssembler {
    fun assemble(fipeVehicle: FipeVehicle, vehicleBrand: VehicleBrand): Vehicle{
        return Vehicle(
                brand = vehicleBrand,
                id = fipeVehicle.id,
                name = fipeVehicle.name
        )
    }
}