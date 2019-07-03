package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.gateways.domains.FipeVehicleModelResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class FipeVehicleModelToVehicleModelAssemblerSpec : Spek({

    val fipeVehicleModelToVehicleModelAssembler by memoized { FipeVehicleModelToVehicleModelAssembler() }

    describe("#assemble") {

        it("convert entity with all attributes correctly") {
            val response = fipeVehicleModelToVehicleModelAssembler.assemble(
                FipeVehicleModelResponse(
                    fipe_codigo = "1987-1",
                    fipe_marca = "Fiat",
                    id = "1987-1",
                    key = "1987-1",
                    marca = "FIAT",
                    name = "1987 Gasolina",
                    veiculo = "147 C/ CL"),
                Vehicle(
                    brand = VehicleBrand(
                        id = 21,
                        name = "Fiat",
                        vehicleType = VehicleType.CARS),
                    id = "437",
                    name = "147 C/ CL"))

            with(response) {
                assertThat(id, equalTo("1987-1"))
                assertThat(name, equalTo("1987 Gasolina"))
                assertThat(vehicle, equalTo(
                    Vehicle(
                        brand = VehicleBrand(
                            id = 21,
                            name = "Fiat",
                            vehicleType = VehicleType.CARS),
                        id = "437",
                        name = "147 C/ CL")))
            }
        }

        it("convert entity without optional attributes correctly") {
            val response = fipeVehicleModelToVehicleModelAssembler.assemble(
                FipeVehicleModelResponse(
                    fipe_codigo = null,
                    fipe_marca = null,
                    id = "1987-1",
                    key = null,
                    marca = null,
                    name = "1987 Gasolina",
                    veiculo = null),
                Vehicle(
                    brand = VehicleBrand(
                        id = 21,
                        name = null,
                        vehicleType = VehicleType.CARS),
                    id = "437",
                    name = null))

            with(response) {
                assertThat(id, equalTo("1987-1"))
                assertThat(name, equalTo("1987 Gasolina"))
                assertThat(vehicle, equalTo(
                    Vehicle(
                        brand = VehicleBrand(
                            id = 21,
                            name = null,
                            vehicleType = VehicleType.CARS),
                        id = "437",
                        name = null)))
            }
        }
    }
})