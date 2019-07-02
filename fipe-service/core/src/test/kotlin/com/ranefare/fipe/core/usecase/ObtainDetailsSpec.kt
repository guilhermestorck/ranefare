package com.ranefare.fipe.core.usecase

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleDetails
import com.ranefare.fipe.core.domains.VehicleModel
import com.ranefare.fipe.core.domains.exceptions.FipeIntegrationException
import com.ranefare.fipe.core.gateways.FipeGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ObtainDetailsSpec : Spek({

    val fipeGateway by memoized { mockk<FipeGateway>() }
    val obtainDetails by memoized { ObtainDetails(fipeGateway) }

    describe("#execute") {

        it("should call the gateway correctly") {
            val vehicle = mockk<Vehicle>()
            val vehicleModel1 = mockk<VehicleModel>()
            val vehicleModel2 = mockk<VehicleModel>()
            val vehicleModel3 = mockk<VehicleModel>()
            val vehicleModels = listOf(vehicleModel1, vehicleModel2, vehicleModel3)
            val vehicleDetails = mockk<VehicleDetails>()

            every { fipeGateway.getVehicleModels(vehicle) } returns vehicleModels
            every { fipeGateway.getVehiclePrice(vehicleModel1) } returns vehicleDetails

            val result = obtainDetails.execute(vehicle)

            assertThat(result, equalTo(vehicleDetails))

            verify(exactly = 1) { fipeGateway.getVehicleModels(vehicle) }
            verify(exactly = 1) { fipeGateway.getVehiclePrice(vehicleModel1) }
        }

        it("should throw exception when the getVehicleModels gateway throws exception") {
            val vehicle = mockk<Vehicle>()
            val vehicleDetails = mockk<VehicleDetails>()
            val throwable = mockk<Throwable>()

            every { fipeGateway.getVehicleModels(vehicle) } throws FipeIntegrationException("test", throwable)
            every { fipeGateway.getVehiclePrice(any()) } returns vehicleDetails

            try {
                obtainDetails.execute(vehicle)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(throwable, equalTo(throwable))
                }
            }

            verify(exactly = 1) { fipeGateway.getVehicleModels(vehicle) }
            verify(exactly = 0) { fipeGateway.getVehiclePrice(any()) }
        }

        it("should throw exception when the getVehiclePrice gateway throws exception") {
            val vehicle = mockk<Vehicle>()
            val throwable = mockk<Throwable>()
            val vehicleModel1 = mockk<VehicleModel>()
            val vehicleModel2 = mockk<VehicleModel>()
            val vehicleModel3 = mockk<VehicleModel>()
            val vehicleModels = listOf(vehicleModel1, vehicleModel2, vehicleModel3)

            every { fipeGateway.getVehicleModels(vehicle) } returns vehicleModels
            every { fipeGateway.getVehiclePrice(vehicleModel1) } throws FipeIntegrationException("test", throwable)

            try {
                obtainDetails.execute(vehicle)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(throwable, equalTo(throwable))
                }
            }

            verify(exactly = 1) { fipeGateway.getVehicleModels(vehicle) }
            verify(exactly = 1) { fipeGateway.getVehiclePrice(any()) }
        }
    }
})