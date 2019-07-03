package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.gateways.domains.FipeVehicleBrandResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class FipeVehicleBrandToVehicleBrandAssemblerSpec : Spek({

    val fipeVehicleBrandToVehicleBrandAssembler by memoized { FipeVehicleBrandToVehicleBrandAssembler() }

    describe("#assemble") {

        it("convert entity with all attributes correctly") {
            val response = fipeVehicleBrandToVehicleBrandAssembler.assemble(
                FipeVehicleBrandResponse(
                    fipeName = "Audi",
                    id = 6,
                    key = "audi-6",
                    name = "AUDI",
                    order = 2),
                VehicleType.CARS)

            with(response) {
                assertThat(id, equalTo(6))
                assertThat(name, equalTo("AUDI"))
                assertThat(vehicleType, equalTo(VehicleType.CARS))
            }
        }

        it("convert entity without optional attributes correctly") {
            val response = fipeVehicleBrandToVehicleBrandAssembler.assemble(
                FipeVehicleBrandResponse(
                    fipeName = null,
                    id = 6,
                    key = null,
                    name = "AUDI",
                    order = null),
                VehicleType.CARS)

            with(response) {
                assertThat(id, equalTo(6))
                assertThat(name, equalTo("AUDI"))
                assertThat(vehicleType, equalTo(VehicleType.CARS))
            }
        }
    }
})