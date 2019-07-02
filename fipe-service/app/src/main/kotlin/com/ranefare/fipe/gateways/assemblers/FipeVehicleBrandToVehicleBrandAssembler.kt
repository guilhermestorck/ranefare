package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.gateways.domains.FipeVehicleBrand
import javax.inject.Singleton

@Singleton
class FipeVehicleBrandToVehicleBrandAssembler {
    fun assemble(fipeVehicleBrand: FipeVehicleBrand, vehicleType: VehicleType): VehicleBrand {
        return VehicleBrand(
                id = fipeVehicleBrand.id,
                name = fipeVehicleBrand.name,
                vehicleType = vehicleType
        )
    }
}