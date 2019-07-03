package com.ranefare.fipe.core.usecase

import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleType
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

class ObtainVehicleBrandsSpec : Spek({

    val fipeGateway by memoized { mockk<FipeGateway>() }
    val obtainVehicleBrands by memoized { ObtainVehicleBrands(fipeGateway) }

    describe("#execute") {

        it("should call the gateway correctly") {
            val vehicleType = mockk<VehicleType>()
            val vehicleBrands = listOf<VehicleBrand>(mockk(), mockk())

            every { fipeGateway.getBrands(vehicleType) } returns vehicleBrands

            val result = obtainVehicleBrands.execute(vehicleType)

            assertThat(result, equalTo(vehicleBrands))

            verify(exactly = 1) { fipeGateway.getBrands(vehicleType) }
        }

        it("should throw exception when the getBrands gateway throws exception") {
            val vehicleType = mockk<VehicleType>()
            val throwable = mockk<Throwable>()

            every { fipeGateway.getBrands(vehicleType) } throws FipeIntegrationException("test", throwable)

            try {
                obtainVehicleBrands.execute(vehicleType)
                fail("expected an exception")
            } catch (e: FipeIntegrationException) {
                with(e) {
                    assertThat(message, equalTo("test"))
                    assertThat(cause, equalTo(throwable))
                }
            }

            verify(exactly = 1) { fipeGateway.getBrands(vehicleType) }
        }
    }
})