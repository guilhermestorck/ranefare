package com.ranefare.fipe.controllers.converters

import com.ranefare.fipe.contract.domains.getModels.ModelResponse
import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class VehiclesToGetModelsResponseConverterSpec : Spek({

    val vehiclesToGetModelsResponseConverter by memoized { VehiclesToGetModelsResponseConverter() }

    describe("#assemble") {

        it("assemble many entities correctly") {
            val vehicles = listOf(
                Vehicle(
                    brand = VehicleBrand(id = 21, name = "Fiat", vehicleType = VehicleType.CARS),
                    id = "437",
                    name = "147 C/ CL"
                ),
                Vehicle(
                    brand = VehicleBrand(id = 21, name = "Fiat", vehicleType = VehicleType.CARS),
                    id = "438",
                    name = "147 Furgão (todos)"
                ),
                Vehicle(
                    brand = VehicleBrand(id = 21, name = "Fiat", vehicleType = VehicleType.CARS),
                    id = "439",
                    name = "147 Pick-Up (todas)"
                )
            )

            val response = vehiclesToGetModelsResponseConverter.assemble(vehicles)

            with(response) {
                assertThat(models, hasSize(3))
                assertThat(models, hasItem(ModelResponse(brandId = 21, modelId = "437", modelName = "147 C/ CL")))
                assertThat(models, hasItem(ModelResponse(brandId = 21, modelId = "438", modelName = "147 Furgão (todos)")))
                assertThat(models, hasItem(ModelResponse(brandId = 21, modelId = "439", modelName = "147 Pick-Up (todas)")))
            }
        }

        it("don't assemble when has entity with null name") {
            val vehicles = listOf(
                Vehicle(
                    brand = VehicleBrand(id = 21, name = "Fiat", vehicleType = VehicleType.CARS),
                    id = "437",
                    name = "147 C/ CL"
                ),
                Vehicle(
                    brand = VehicleBrand(id = 21, name = "Fiat", vehicleType = VehicleType.CARS),
                    id = "438",
                    name = null
                ),
                Vehicle(
                    brand = VehicleBrand(id = 21, name = "Fiat", vehicleType = VehicleType.CARS),
                    id = "439",
                    name = "147 Pick-Up (todas)"
                )
            )


            try {
                vehiclesToGetModelsResponseConverter.assemble(vehicles)
                fail("expected an exception")
            } catch (ex: NullPointerException) {
            }
        }
    }
})