package com.ranefare.fipe.gateways.clients

import com.ranefare.fipe.gateways.domains.FipeVehicleBrandResponse
import com.ranefare.fipe.gateways.domains.FipeVehicleDetailsResponse
import com.ranefare.fipe.gateways.domains.FipeVehicleModelResponse
import com.ranefare.fipe.gateways.domains.FipeVehicleResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("\${gateways.fipe.endpoint}")
interface FipeClient {

    @Get("/{vehicleType}/marcas.json")
    fun getBrands(@PathVariable vehicleType: String): HttpResponse<List<FipeVehicleBrandResponse>>

    @Get("/{vehicleType}/veiculos/{brandId}.json")
    fun getVehicles(@PathVariable vehicleType: String, @PathVariable brandId: Int): HttpResponse<List<FipeVehicleResponse>>

    @Get("/{vehicleType}/veiculo/{brandId}/{vehicleId}.json")
    fun getVehicleModels(
        @PathVariable vehicleType: String,
        @PathVariable brandId: Int,
        @PathVariable vehicleId: String): HttpResponse<List<FipeVehicleModelResponse>>

    @Get("/{vehicleType}/veiculo/{brandId}/{vehicleId}/{modelId}.json")
    fun getPrice(@PathVariable vehicleType: String,
                 @PathVariable brandId: Int,
                 @PathVariable vehicleId: String,
                 @PathVariable modelId: String): HttpResponse<FipeVehicleDetailsResponse>
}