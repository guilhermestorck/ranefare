package com.ranefare.fipe.gateways

import com.ranefare.fipe.core.domains.*
import com.ranefare.fipe.core.domains.exceptions.FipeIntegrationException
import com.ranefare.fipe.gateways.assemblers.FipeVehicleBrandToVehicleBrandAssembler
import com.ranefare.fipe.gateways.assemblers.FipeVehicleDetailsToVehicleDetailsAssembler
import com.ranefare.fipe.gateways.assemblers.FipeVehicleModelToVehicleModelAssembler
import com.ranefare.fipe.gateways.assemblers.FipeVehicleToVehicleAssembler
import com.ranefare.fipe.gateways.clients.FipeClient
import com.ranefare.fipe.gateways.domains.FipeVehicleBrandResponse
import com.ranefare.fipe.gateways.domains.FipeVehicleDetailsResponse
import com.ranefare.fipe.gateways.domains.FipeVehicleModelResponse
import com.ranefare.fipe.gateways.domains.FipeVehicleResponse
import io.micronaut.http.HttpResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class FipeGatewayImplSpec : Spek({

    val fipeClient by memoized { mockk<FipeClient>() }
    val fipeVehicleBrandToVehicleBrandAssembler by memoized { mockk<FipeVehicleBrandToVehicleBrandAssembler>() }
    val fipeVehicleToVehicleAssembler by memoized { mockk<FipeVehicleToVehicleAssembler>() }
    val fipeVehicleModelToVehicleModelAssembler by memoized { mockk<FipeVehicleModelToVehicleModelAssembler>() }
    val fipeVehicleDetailsToVehicleDetailsAssembler by memoized { mockk<FipeVehicleDetailsToVehicleDetailsAssembler>() }
    val fipeGatewayImpl by memoized {
        FipeGatewayImpl(
            fipeClient,
            fipeVehicleBrandToVehicleBrandAssembler,
            fipeVehicleToVehicleAssembler,
            fipeVehicleModelToVehicleModelAssembler,
            fipeVehicleDetailsToVehicleDetailsAssembler)
    }

    describe("#getBrands") {

        it("call the dependencies correctly") {
            val fipeVehicleBrand1 = mockk<FipeVehicleBrandResponse>()
            val fipeVehicleBrand2 = mockk<FipeVehicleBrandResponse>()
            val fipeVehicleBrands = listOf(fipeVehicleBrand1, fipeVehicleBrand2)
            val vehicleBrand1 = mockk<VehicleBrand>()
            val vehicleBrand2 = mockk<VehicleBrand>()

            every { fipeClient.getBrands("carros") } returns HttpResponse.ok(fipeVehicleBrands)
            every { fipeVehicleBrandToVehicleBrandAssembler.assemble(fipeVehicleBrand1, VehicleType.CARS) } returns vehicleBrand1
            every { fipeVehicleBrandToVehicleBrandAssembler.assemble(fipeVehicleBrand2, VehicleType.CARS) } returns vehicleBrand2

            val response = fipeGatewayImpl.getBrands(VehicleType.CARS)

            assertThat(response, hasSize(2))
            assertThat(response, hasItem(vehicleBrand1))
            assertThat(response, hasItem(vehicleBrand2))

            verify(exactly = 1) { fipeClient.getBrands("carros") }
            verify(exactly = 1) { fipeVehicleBrandToVehicleBrandAssembler.assemble(fipeVehicleBrand1, VehicleType.CARS) }
            verify(exactly = 1) { fipeVehicleBrandToVehicleBrandAssembler.assemble(fipeVehicleBrand2, VehicleType.CARS) }
        }

        it("throw exception when the client throws an error") {
            val throwable = mockk<Throwable>()
            val clientException = FipeIntegrationException("test", throwable)

            every { fipeClient.getBrands("carros") } throws clientException

            try {
                fipeGatewayImpl.getBrands(VehicleType.CARS)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(cause, equalTo(clientException as Throwable))
                }
            }

            verify(exactly = 1) { fipeClient.getBrands("carros") }
        }

        it("throw exception when the client returns empty response") {
            every { fipeClient.getBrands("carros") } returns HttpResponse.ok()

            try {
                fipeGatewayImpl.getBrands(VehicleType.CARS)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("Empty result."))
                    assertThat(cause, instanceOf(FipeIntegrationException::class.java))
                }
            }

            verify(exactly = 1) { fipeClient.getBrands("carros") }
        }
    }

    describe("#getVehicles") {

        it("call the dependencies correctly") {
            val vehicleBrand = VehicleBrand(
                id = 21,
                name = null,
                vehicleType = VehicleType.CARS)
            val fipeVehicle1 = mockk<FipeVehicleResponse>()
            val fipeVehicle2 = mockk<FipeVehicleResponse>()
            val fipeVehicles = listOf(fipeVehicle1, fipeVehicle2)
            val vehicle1 = mockk<Vehicle>()
            val vehicle2 = mockk<Vehicle>()

            every { fipeClient.getVehicles("carros", 21) } returns HttpResponse.ok(fipeVehicles)
            every { fipeVehicleToVehicleAssembler.assemble(fipeVehicle1, vehicleBrand) } returns vehicle1
            every { fipeVehicleToVehicleAssembler.assemble(fipeVehicle2, vehicleBrand) } returns vehicle2

            val response = fipeGatewayImpl.getVehicles(vehicleBrand)

            assertThat(response, hasSize(2))
            assertThat(response, hasItem(vehicle1))
            assertThat(response, hasItem(vehicle2))

            verify(exactly = 1) { fipeClient.getVehicles("carros", 21) }
            verify(exactly = 1) { fipeVehicleToVehicleAssembler.assemble(fipeVehicle1, vehicleBrand) }
            verify(exactly = 1) { fipeVehicleToVehicleAssembler.assemble(fipeVehicle2, vehicleBrand) }
        }

        it("throw exception when the client throws an error") {
            val throwable = mockk<Throwable>()
            val clientException = FipeIntegrationException("test", throwable)
            val vehicleBrand = VehicleBrand(
                id = 21,
                name = null,
                vehicleType = VehicleType.CARS)

            every { fipeClient.getVehicles("carros", 21) } throws clientException

            try {
                fipeGatewayImpl.getVehicles(vehicleBrand)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(cause, equalTo(clientException as Throwable))
                }
            }

            verify(exactly = 1) { fipeClient.getVehicles("carros", 21) }
        }

        it("throw exception when the client returns empty response") {
            val vehicleBrand = VehicleBrand(
                id = 21,
                name = null,
                vehicleType = VehicleType.CARS)

            every { fipeClient.getVehicles("carros", 21) } returns HttpResponse.ok()

            try {
                fipeGatewayImpl.getVehicles(vehicleBrand)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("Empty result."))
                    assertThat(cause, instanceOf(FipeIntegrationException::class.java))
                }
            }

            verify(exactly = 1) { fipeClient.getVehicles("carros", 21) }
        }
    }

    describe("#getVehicleModels") {

        it("call the dependencies correctly") {
            val vehicle = Vehicle(
                brand = VehicleBrand(
                    id = 21,
                    name = null,
                    vehicleType = VehicleType.CARS),
                id = "437",
                name = null)
            val fipeVehicleModel1 = mockk<FipeVehicleModelResponse>()
            val fipeVehicleModel2 = mockk<FipeVehicleModelResponse>()
            val fipeVehicleModels = listOf(fipeVehicleModel1, fipeVehicleModel2)
            val vehicleModel1 = mockk<VehicleModel>()
            val vehicleModel2 = mockk<VehicleModel>()

            every { fipeClient.getVehicleModels("carros", 21, "437") } returns HttpResponse.ok(fipeVehicleModels)
            every { fipeVehicleModelToVehicleModelAssembler.assemble(fipeVehicleModel1, vehicle) } returns vehicleModel1
            every { fipeVehicleModelToVehicleModelAssembler.assemble(fipeVehicleModel2, vehicle) } returns vehicleModel2

            val response = fipeGatewayImpl.getVehicleModels(vehicle)

            assertThat(response, hasSize(2))
            assertThat(response, hasItem(vehicleModel1))
            assertThat(response, hasItem(vehicleModel2))

            verify(exactly = 1) { fipeClient.getVehicleModels("carros", 21, "437") }
            verify(exactly = 1) { fipeVehicleModelToVehicleModelAssembler.assemble(fipeVehicleModel1, vehicle) }
            verify(exactly = 1) { fipeVehicleModelToVehicleModelAssembler.assemble(fipeVehicleModel1, vehicle) }
        }

        it("throw exception when the client throws an error") {
            val throwable = mockk<Throwable>()
            val clientException = FipeIntegrationException("test", throwable)
            val vehicle = Vehicle(
                brand = VehicleBrand(
                    id = 21,
                    name = null,
                    vehicleType = VehicleType.CARS),
                id = "437",
                name = null)

            every { fipeClient.getVehicleModels("carros", 21, "437") } throws clientException

            try {
                fipeGatewayImpl.getVehicleModels(vehicle)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(cause, equalTo(clientException as Throwable))
                }
            }

            verify(exactly = 1) { fipeClient.getVehicleModels("carros", 21, "437") }
        }

        it("throw exception when the client returns empty response") {
            val vehicle = Vehicle(
                brand = VehicleBrand(
                    id = 21,
                    name = null,
                    vehicleType = VehicleType.CARS),
                id = "437",
                name = null)

            every { fipeClient.getVehicleModels("carros", 21, "437") } returns HttpResponse.ok()

            try {
                fipeGatewayImpl.getVehicleModels(vehicle)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("Empty result."))
                    assertThat(cause, instanceOf(FipeIntegrationException::class.java))
                }
            }

            verify(exactly = 1) { fipeClient.getVehicleModels("carros", 21, "437") }
        }
    }

    describe("#getVehiclePrice") {

        it("call the dependencies correctly") {
            val model = VehicleModel(
                id = "1987-1",
                name = null,
                vehicle = Vehicle(
                    brand = VehicleBrand(
                        id = 21,
                        name = null,
                        vehicleType = VehicleType.CARS),
                    id = "437",
                    name = null))
            val fipeVehicleDetailsResponse = mockk<FipeVehicleDetailsResponse>()
            val vehicleDetails = mockk<VehicleDetails>()

            every { fipeClient.getPrice("carros", 21, "437", "1987-1") } returns HttpResponse.ok(fipeVehicleDetailsResponse)
            every { fipeVehicleDetailsToVehicleDetailsAssembler.assemble(fipeVehicleDetailsResponse, model) } returns vehicleDetails

            val response = fipeGatewayImpl.getVehiclePrice(model)

            assertThat(response, equalTo(vehicleDetails))

            verify(exactly = 1) { fipeClient.getPrice("carros", 21, "437", "1987-1") }
            verify(exactly = 1) { fipeVehicleDetailsToVehicleDetailsAssembler.assemble(fipeVehicleDetailsResponse, model) }
        }

        it("throw exception when the client throws an error") {
            val throwable = mockk<Throwable>()
            val clientException = FipeIntegrationException("test", throwable)
            val model = VehicleModel(
                id = "1987-1",
                name = null,
                vehicle = Vehicle(
                    brand = VehicleBrand(
                        id = 21,
                        name = null,
                        vehicleType = VehicleType.CARS),
                    id = "437",
                    name = null))

            every { fipeClient.getPrice("carros", 21, "437", "1987-1") } throws clientException

            try {
                fipeGatewayImpl.getVehiclePrice(model)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(cause, equalTo(clientException as Throwable))
                }
            }

            verify(exactly = 1) { fipeClient.getPrice("carros", 21, "437", "1987-1") }
        }

        it("throw exception when the client returns empty response") {
            val model = VehicleModel(
                id = "1987-1",
                name = null,
                vehicle = Vehicle(
                    brand = VehicleBrand(
                        id = 21,
                        name = null,
                        vehicleType = VehicleType.CARS),
                    id = "437",
                    name = null))

            every { fipeClient.getPrice("carros", 21, "437", "1987-1") } returns HttpResponse.ok()

            try {
                fipeGatewayImpl.getVehiclePrice(model)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("Empty result."))
                    assertThat(cause, instanceOf(FipeIntegrationException::class.java))
                }
            }

            verify(exactly = 1) { fipeClient.getPrice("carros", 21, "437", "1987-1") }
        }
    }
})