package com.ranefare.fipe.core.usecase

import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
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

class ObtainVehiclesSpec : Spek({

    val fipeGateway by memoized { mockk<FipeGateway>() }
    val obtainVehicles by memoized { ObtainVehicles(fipeGateway) }

    describe("#execute") {

        it("should call the gateway correctly") {
            val vehicleBrand = mockk<VehicleBrand>()
            val vehicles = listOf<Vehicle>(mockk(), mockk())

            every { fipeGateway.getVehicles(vehicleBrand) } returns vehicles

            val result = obtainVehicles.execute(vehicleBrand)

            assertThat(result, equalTo(vehicles))

            verify(exactly = 1) { fipeGateway.getVehicles(vehicleBrand) }
        }

        it("should throw exception when the getVehicles gateway throws exception") {
            val vehicleBrand = mockk<VehicleBrand>()
            val throwable = mockk<Throwable>()

            every { fipeGateway.getVehicles(vehicleBrand) } throws FipeIntegrationException("test", throwable)

            try {
                obtainVehicles.execute(vehicleBrand)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(throwable, equalTo(throwable))
                }
            }

            verify(exactly = 1) { fipeGateway.getVehicles(vehicleBrand) }
        }
    }
})