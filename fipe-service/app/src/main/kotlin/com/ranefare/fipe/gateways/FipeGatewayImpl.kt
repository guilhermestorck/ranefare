package com.ranefare.fipe.gateways

import com.ranefare.fipe.core.domains.*
import com.ranefare.fipe.core.domains.exceptions.FipeIntegrationException
import com.ranefare.fipe.core.gateways.FipeGateway
import com.ranefare.fipe.gateways.assemblers.FipeVehicleBrandToVehicleBrandAssembler
import com.ranefare.fipe.gateways.assemblers.FipeVehicleDetailsToVehicleDetailsAssembler
import com.ranefare.fipe.gateways.assemblers.FipeVehicleModelToVehicleModelAssembler
import com.ranefare.fipe.gateways.assemblers.FipeVehicleToVehicleAssembler
import com.ranefare.fipe.gateways.clients.FipeClient
import com.ranefare.fipe.gateways.domains.FipeVehicleType
import javax.inject.Singleton

@Singleton
class FipeGatewayImpl(
    private val fipeClient: FipeClient,
    private val fipeVehicleBrandToVehicleBrandAssembler: FipeVehicleBrandToVehicleBrandAssembler,
    private val fipeVehicleToVehicleAssembler: FipeVehicleToVehicleAssembler,
    private val fipeVehicleModelToVehicleModelAssembler: FipeVehicleModelToVehicleModelAssembler,
    private val fipeVehicleDetailsToVehicleDetailsAssembler: FipeVehicleDetailsToVehicleDetailsAssembler
) : FipeGateway {
    override fun getBrands(vehicleType: VehicleType): List<VehicleBrand> {
        try {
            val response = fipeClient.getBrands(FipeVehicleType.valueOf(vehicleType.name).label)

            val fipeVehicleBrands = response.body() ?: throw FipeIntegrationException("Empty result")

            return fipeVehicleBrands.map { fipeVehicleBrand -> fipeVehicleBrandToVehicleBrandAssembler.assemble(fipeVehicleBrand, vehicleType) }
        } catch (e: Exception) {
            throw FipeIntegrationException(message = e.message, cause = e)
        }
    }

    override fun getVehicles(vehicleBrand: VehicleBrand): List<Vehicle> {
        try {
            val fipeVehicles = fipeClient.getVehicles(
                FipeVehicleType.valueOf(vehicleBrand.vehicleType.name).label,
                vehicleBrand.id).body() ?: throw FipeIntegrationException("Empty result")

            return fipeVehicles.map { fipeVehicle -> fipeVehicleToVehicleAssembler.assemble(fipeVehicle, vehicleBrand) }
        } catch (e: Exception) {
            throw FipeIntegrationException(message = e.message, cause = e)
        }
    }

    override fun getVehicleModels(vehicle: Vehicle): List<VehicleModel> {
        try {
            val fipeVehicleModels = fipeClient.getVehicleModels(
                FipeVehicleType.valueOf(vehicle.brand.vehicleType.name).label,
                vehicle.brand.id,
                vehicle.id).body() ?: throw FipeIntegrationException("Empty result")

            return fipeVehicleModels.map { fipeVehicleModel -> fipeVehicleModelToVehicleModelAssembler.assemble(fipeVehicleModel, vehicle) }
        } catch (e: Exception) {
            throw FipeIntegrationException(message = e.message, cause = e)
        }
    }

    override fun getVehiclePrice(vehicleModel: VehicleModel): VehicleDetails {
        try {
            val fipeVehicleDetails = fipeClient.getPrice(
                FipeVehicleType.valueOf(vehicleModel.vehicle.brand.vehicleType.name).label,
                vehicleModel.vehicle.brand.id,
                vehicleModel.vehicle.id,
                vehicleModel.id).body() ?: throw FipeIntegrationException("Empty result")

            return fipeVehicleDetailsToVehicleDetailsAssembler.assemble(fipeVehicleDetails, vehicleModel)
        } catch (e: Exception) {
            throw FipeIntegrationException(message = e.message, cause = e)
        }
    }
}