package com.ranefare.fipe.controllers.converters

import com.ranefare.fipe.core.domains.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class VehicleDetailsToGetDetailsResponseConverterSpec : Spek({

    val vehicleDetailsToGetDetailsResponseConverter by memoized { VehicleDetailsToGetDetailsResponseConverter() }

    describe("#convert") {

        it("convert many entities correctly") {
            val vehicleDetails = VehicleDetails(
                fuel = "Gasolina",
                model = VehicleModel(
                    id = "1987-1",
                    name = "1987 Gasolina",
                    vehicle = Vehicle(
                        id = "437",
                        name = "147 C/ CL",
                        brand = VehicleBrand(id = 21, name = "Fiat", vehicleType = VehicleType.CARS)
                    )
                ),
                price = "R$ 2.856,00",
                year = "1987"
            )

            val response = vehicleDetailsToGetDetailsResponseConverter.convert(vehicleDetails)

            with(response) {
                assertThat(brandId, equalTo(21))
                assertThat(brandName, equalTo("Fiat"))
                assertThat(fuel, equalTo("Gasolina"))
                assertThat(modelId, equalTo("437"))
                assertThat(modelName, equalTo("147 C/ CL"))
                assertThat(price, equalTo("R$ 2.856,00"))
                assertThat(year, equalTo("1987"))
            }
        }

        it("don't convert when has entity with null brandName") {
            val vehicleDetails = VehicleDetails(
                fuel = "Gasolina",
                model = VehicleModel(
                    id = "1987-1",
                    name = "1987 Gasolina",
                    vehicle = Vehicle(
                        id = "437",
                        name = null,
                        brand = VehicleBrand(id = 21, name = "FIAT", vehicleType = VehicleType.CARS)
                    )
                ),
                price = "R$ 2.856,00",
                year = "1987"
            )

            try {
                vehicleDetailsToGetDetailsResponseConverter.convert(vehicleDetails)
                fail("expected an exception")
            } catch (ex: NullPointerException) {
            }
        }

        it("don't convert when has entity with null modelName") {
            val vehicleDetails = VehicleDetails(
                fuel = "Gasolina",
                model = VehicleModel(
                    id = "1987-1",
                    name = "1987 Gasolina",
                    vehicle = Vehicle(
                        id = "437",
                        name = "147 C/ CL",
                        brand = VehicleBrand(id = 21, name = null, vehicleType = VehicleType.CARS)
                    )
                ),
                price = "R$ 2.856,00",
                year = "1987"
            )

            try {
                vehicleDetailsToGetDetailsResponseConverter.convert(vehicleDetails)
                fail("expected an exception")
            } catch (ex: NullPointerException) {
            }
        }
    }
})