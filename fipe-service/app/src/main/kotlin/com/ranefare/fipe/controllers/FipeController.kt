package com.ranefare.fipe.controllers

import com.ranefare.fipe.contract.contracts.FipeContract
import com.ranefare.fipe.contract.domains.getBrands.GetBrandsResponse
import com.ranefare.fipe.contract.domains.getDetails.GetDetailsResponse
import com.ranefare.fipe.contract.domains.getModels.GetModelsResponse
import com.ranefare.fipe.controllers.converters.VehicleBrandsToGetBrandsResponse
import com.ranefare.fipe.controllers.converters.VehicleDetailsToGetDetailsResponse
import com.ranefare.fipe.controllers.converters.VehiclesToGetModelsResponse
import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.core.usecase.ObtainDetails
import com.ranefare.fipe.core.usecase.ObtainVehicleBrands
import com.ranefare.fipe.core.usecase.ObtainVehicles
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/fipe")
class FipeController(
        private val obtainVehicleBrands: ObtainVehicleBrands,
        private val obtainVehicles: ObtainVehicles,
        private val obtainDetails: ObtainDetails,
        private val vehicleBrandsToGetBrandsResponse: VehicleBrandsToGetBrandsResponse,
        private val vehiclesToGetModelsResponse: VehiclesToGetModelsResponse,
        private val vehicleDetailsToGetDetailsResponse: VehicleDetailsToGetDetailsResponse
) : FipeContract {

    @Get("/brands")
    override fun getBrands(): HttpResponse<GetBrandsResponse> {
        return HttpResponse.ok(
                vehicleBrandsToGetBrandsResponse.assemble(
                        obtainVehicleBrands.execute(VehicleType.CARS)
                )
        )
    }

    @Get("/brands/{brandId}/models")
    override fun getModels(brandId: Int): HttpResponse<GetModelsResponse> {
        return HttpResponse.ok(
                vehiclesToGetModelsResponse.assemble(
                        obtainVehicles.execute(VehicleBrand(
                                id = brandId,
                                name = null,
                                vehicleType = VehicleType.CARS
                        ))
                )
        )
    }

    @Get("/brands/{brandId}/models/{modelId}/details")
    override fun getDetails(brandId: Int, modelId: String): HttpResponse<GetDetailsResponse> {
        return HttpResponse.ok(
                vehicleDetailsToGetDetailsResponse.assemble(
                        obtainDetails.execute(Vehicle(
                                id = modelId,
                                name = null,
                                brand = VehicleBrand(
                                    id = brandId,
                                    name = null,
                                    vehicleType = VehicleType.CARS
                                )
                        ))
                )
        )
    }
}