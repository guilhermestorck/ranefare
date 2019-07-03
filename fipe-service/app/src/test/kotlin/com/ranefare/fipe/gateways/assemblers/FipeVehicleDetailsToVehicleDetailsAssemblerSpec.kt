package com.ranefare.fipe.gateways.assemblers

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleModel
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.gateways.domains.FipeVehicleDetailsResponse
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class FipeVehicleDetailsToVehicleDetailsAssemblerSpec : Spek({

    val fipeVehicleDetailsToVehicleDetailsAssembler by memoized { FipeVehicleDetailsToVehicleDetailsAssembler() }

    describe("#assemble") {

        it("convert entity with all attributes correctly") {
            val response = fipeVehicleDetailsToVehicleDetailsAssembler.assemble(
                FipeVehicleDetailsResponse(
                    ano_modelo = "1987",
                    combustivel = "Gasolina",
                    fipe_codigo = "001124-0",
                    id = "1987",
                    key = "147-1987",
                    marca = "Fiat",
                    name = "147 C/ CL",
                    preco = "R$ 2.856,00",
                    referencia = "julho de 2019",
                    time = 0.010000000000005116,
                    veiculo = "147 C/ CL"),
                VehicleModel(
                    id = "1987-2",
                    name = "1987 Gasolina",
                    vehicle = Vehicle(
                        brand = VehicleBrand(
                            id = 21,
                            name = "Fiat",
                            vehicleType = VehicleType.CARS),
                        id = "437",
                        name = "test name")))

            with(response) {
                assertThat(fuel, equalTo("Gasolina"))
                assertThat(model, equalTo(
                    VehicleModel(
                        id = "1987-2",
                        name = "1987 Gasolina",
                        vehicle = Vehicle(
                            brand = VehicleBrand(
                                id = 21,
                                name = "Fiat",
                                vehicleType = VehicleType.CARS), id = "437",
                            name = "test name"))))

                assertThat(price, equalTo("R$ 2.856,00"))
                assertThat(year, equalTo("1987"))
            }
        }

        it("convert entity without optional attributes correctly") {
            val response = fipeVehicleDetailsToVehicleDetailsAssembler.assemble(
                FipeVehicleDetailsResponse(
                    ano_modelo = "1987",
                    combustivel = "Gasolina",
                    fipe_codigo = null,
                    id = null,
                    key = null,
                    marca = null,
                    name = "147 C/ CL",
                    preco = "R$ 2.856,00",
                    referencia = null,
                    time = null,
                    veiculo = null),
                VehicleModel(
                    id = "1987-2",
                    name = null,
                    vehicle = Vehicle(
                        brand = VehicleBrand(
                            id = 21,
                            name = null,
                            vehicleType = VehicleType.CARS),
                        id = "437",
                        name = null)))

            with(response) {
                assertThat(fuel, equalTo("Gasolina"))
                assertThat(model, equalTo(
                    VehicleModel(
                        id = "1987-2",
                        name = null,
                        vehicle = Vehicle(
                            brand = VehicleBrand(
                                id = 21,
                                name = null,
                                vehicleType = VehicleType.CARS),
                            id = "437",
                            name = "147 C/ CL"))))
                
                assertThat(price, equalTo("R$ 2.856,00"))
                assertThat(year, equalTo("1987"))
            }
        }
    }
})