package com.ranefare.fipe.controllers.converters

import com.ranefare.fipe.contract.domains.getBrands.BrandResponse
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class VehicleBrandsToGetBrandsResponseConverterSpec : Spek({

    val vehicleBrandsToGetBrandsResponseConverter by memoized { VehicleBrandsToGetBrandsResponseConverter() }

    describe("#assemble") {

        it("assemble many entities correctly") {
            val vehicleBrands = listOf(
                VehicleBrand(id = 6, name = "Audi", vehicleType = VehicleType.CARS),
                VehicleBrand(id = 7, name = "BMW", vehicleType = VehicleType.CARS),
                VehicleBrand(id = 13, name = "Citroën", vehicleType = VehicleType.CARS)
            )

            val response = vehicleBrandsToGetBrandsResponseConverter.assemble(vehicleBrands)

            with(response) {
                assertThat(brands, hasSize(3))
                assertThat(brands, hasItem(BrandResponse(brandId = 6, brandName = "Audi")))
                assertThat(brands, hasItem(BrandResponse(brandId = 7, brandName = "BMW")))
                assertThat(brands, hasItem(BrandResponse(brandId = 13, brandName = "Citroën")))
            }
        }

        it("don't assemble when has entity with null name") {
            val vehicleBrands = listOf(
                VehicleBrand(id = 6, name = "Audi", vehicleType = VehicleType.CARS),
                VehicleBrand(id = 7, name = null, vehicleType = VehicleType.CARS),
                VehicleBrand(id = 13, name = "Citroën", vehicleType = VehicleType.CARS)
            )

            try {
                vehicleBrandsToGetBrandsResponseConverter.assemble(vehicleBrands)
                fail("expected an exception")
            } catch (ex: NullPointerException) {
            }
        }
    }
})