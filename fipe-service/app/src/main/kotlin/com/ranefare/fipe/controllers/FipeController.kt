package com.ranefare.fipe.controllers

import com.ranefare.fipe.contract.contracts.GetBrandsContract
import com.ranefare.fipe.contract.contracts.GetDetailsContract
import com.ranefare.fipe.contract.contracts.GetModelsContract
import com.ranefare.fipe.contract.domains.getBrands.GetBrandsResponse
import com.ranefare.fipe.contract.domains.getDetails.GetDetailsResponse
import com.ranefare.fipe.contract.domains.getModels.GetModelsResponse
import com.ranefare.fipe.controllers.converters.VehicleBrandsToGetBrandsResponseConverter
import com.ranefare.fipe.controllers.converters.VehicleDetailsToGetDetailsResponseConverter
import com.ranefare.fipe.controllers.converters.VehiclesToGetModelsResponseConverter
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
    private val vehicleBrandsToGetBrandsResponseConverter: VehicleBrandsToGetBrandsResponseConverter,
    private val vehiclesToGetModelsResponseConverter: VehiclesToGetModelsResponseConverter,
    private val vehicleDetailsToGetDetailsResponseConverter: VehicleDetailsToGetDetailsResponseConverter
) : GetModelsContract, GetBrandsContract, GetDetailsContract {

    @Get("/brands")
    override fun getBrands(): HttpResponse<GetBrandsResponse> = HttpResponse.ok(
        vehicleBrandsToGetBrandsResponseConverter.convert(
            obtainVehicleBrands.execute(VehicleType.CARS)
        )
    )

    @Get("/brands/{brandId}/models")
    override fun getModels(brandId: Int): HttpResponse<GetModelsResponse> {
        return HttpResponse.ok(
            vehiclesToGetModelsResponseConverter.convert(
                obtainVehicles.execute(
                    VehicleBrand(
                        id = brandId,
                        name = null,
                        vehicleType = VehicleType.CARS
                    )
                )
            )
        )
    }

    @Get("/brands/{brandId}/models/{modelId}/details")
    override fun getDetails(brandId: Int, modelId: String): HttpResponse<GetDetailsResponse> {
        return HttpResponse.ok(
            vehicleDetailsToGetDetailsResponseConverter.convert(
                obtainDetails.execute(
                    Vehicle(
                        id = modelId,
                        name = null,
                        brand = VehicleBrand(
                            id = brandId,
                            name = null,
                            vehicleType = VehicleType.CARS
                        )
                    )
                )
            )
        )
    }
}