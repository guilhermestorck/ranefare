package com.ranefare.fipe.gateways.clients

import com.ranefare.fipe.gateways.domains.FipeVehicle
import com.ranefare.fipe.gateways.domains.FipeVehicleBrand
import com.ranefare.fipe.gateways.domains.FipeVehicleDetails
import com.ranefare.fipe.gateways.domains.FipeVehicleModel
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("http://fipeapi.appspot.com/api/1")
interface FipeClient {

    @Get("/{vehicleType}/marcas.json")
    fun getBrands(@PathVariable vehicleType: String): HttpResponse<List<FipeVehicleBrand>>

    @Get("/{vehicleType}/veiculos/{brandId}.json")
    fun getVehicles(@PathVariable vehicleType: String, @PathVariable brandId: Int): HttpResponse<List<FipeVehicle>>

    @Get("/{vehicleType}/veiculo/{brandId}/{vehicleId}.json")
    fun getVehicleModels(
            @PathVariable vehicleType: String,
            @PathVariable brandId: Int,
            @PathVariable vehicleId: String): HttpResponse<List<FipeVehicleModel>>

    @Get("/{vehicleType}/veiculo/{brandId}/{vehicleId}/{modelId}.json")
    fun getPrice(@PathVariable vehicleType: String,
            @PathVariable brandId: Int,
            @PathVariable vehicleId: String,
            @PathVariable modelId: String): HttpResponse<FipeVehicleDetails>
}