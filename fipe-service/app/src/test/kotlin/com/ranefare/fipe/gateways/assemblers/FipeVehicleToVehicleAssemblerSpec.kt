package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.gateways.domains.FipeVehicleResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class FipeVehicleToVehicleAssemblerSpec : Spek({

    val fipeVehicleToVehicleAssembler by memoized { FipeVehicleToVehicleAssembler() }

    describe("#assemble") {

        it("convert entity with all attributes correctly") {
            val response = fipeVehicleToVehicleAssembler.assemble(
                FipeVehicleResponse(
                    fipe_marca = "Fiat",
                    fipe_name = "147 C/ CL",
                    id = "437",
                    key = "147-437",
                    marca = "FIAT",
                    name = "147 C/ CL"),
                VehicleBrand(
                    id = 21,
                    name = "Fiat",
                    vehicleType = VehicleType.CARS))

            with(response) {
                assertThat(id, equalTo("437"))
                assertThat(name, equalTo("147 C/ CL"))
                assertThat(brand, equalTo(
                    VehicleBrand(
                        id = 21,
                        name = "Fiat",
                        vehicleType = VehicleType.CARS)))
            }
        }

        it("convert entity without optional attributes correctly") {
            val response = fipeVehicleToVehicleAssembler.assemble(
                FipeVehicleResponse(
                    fipe_marca = null,
                    fipe_name = null,
                    id = "437",
                    key = null,
                    marca = null,
                    name = "147 C/ CL"),
                VehicleBrand(
                    id = 21,
                    name = null,
                    vehicleType = VehicleType.CARS))

            with(response) {
                assertThat(id, equalTo("437"))
                assertThat(name, equalTo("147 C/ CL"))
                assertThat(brand, equalTo(
                    VehicleBrand(
                        id = 21,
                        name = null,
                        vehicleType = VehicleType.CARS)))
            }
        }
    }
})