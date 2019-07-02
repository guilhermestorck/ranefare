package com.ranefare.fipe.core.gateways

import com.ranefare.fipe.core.domains.*
import com.ranefare.fipe.core.domains.exceptions.FipeIntegrationException

interface FipeGateway {
    @Throws(FipeIntegrationException::class)
    fun getBrands(vehicleType: VehicleType): List<VehicleBrand>

    @Throws(FipeIntegrationException::class)
    fun getVehicles(vehicleBrand: VehicleBrand): List<Vehicle>

    @Throws(FipeIntegrationException::class)
    fun getVehicleModels(vehicle: Vehicle): List<VehicleModel>

    @Throws(FipeIntegrationException::class)
    fun getVehiclePrice(vehicleModel: VehicleModel): VehicleDetails
}