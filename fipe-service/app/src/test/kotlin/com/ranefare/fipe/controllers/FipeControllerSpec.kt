package  com.ranefare.fipe.controllers

import com.ranefare.fipe.contract.domains.getBrands.GetBrandsResponse
import com.ranefare.fipe.contract.domains.getDetails.GetDetailsResponse
import com.ranefare.fipe.contract.domains.getModels.GetModelsResponse
import com.ranefare.fipe.controllers.converters.VehicleBrandsToGetBrandsResponseConverter
import com.ranefare.fipe.controllers.converters.VehicleDetailsToGetDetailsResponseConverter
import com.ranefare.fipe.controllers.converters.VehiclesToGetModelsResponseConverter
import com.ranefare.fipe.core.domains.Vehicle
import com.ranefare.fipe.core.domains.VehicleBrand
import com.ranefare.fipe.core.domains.VehicleDetails
import com.ranefare.fipe.core.domains.VehicleType
import com.ranefare.fipe.core.usecase.ObtainDetails
import com.ranefare.fipe.core.usecase.ObtainVehicleBrands
import com.ranefare.fipe.core.usecase.ObtainVehicles
import io.micronaut.http.HttpStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class FipeControllerSpec : Spek({
    val obtainVehicleBrands by memoized { mockk<ObtainVehicleBrands>() }
    val obtainVehicles by memoized { mockk<ObtainVehicles>() }
    val obtainDetails by memoized { mockk<ObtainDetails>() }
    val vehicleBrandsToGetBrandsResponseConverter by memoized { mockk<VehicleBrandsToGetBrandsResponseConverter>() }
    val vehiclesToGetModelsResponseConverter by memoized { mockk<VehiclesToGetModelsResponseConverter>() }
    val vehicleDetailsToGetDetailsResponseConverter by memoized { mockk<VehicleDetailsToGetDetailsResponseConverter>() }
    val fipeController by memoized {
        FipeController(
            obtainVehicleBrands,
            obtainVehicles,
            obtainDetails,
            vehicleBrandsToGetBrandsResponseConverter,
            vehiclesToGetModelsResponseConverter,
            vehicleDetailsToGetDetailsResponseConverter)
    }

    describe("#getBrands") {

        it("call the dependencies correctly") {
            val vehicleBrand1 = mockk<VehicleBrand>()
            val vehicleBrand2 = mockk<VehicleBrand>()
            val vehicleBrands = listOf(vehicleBrand1, vehicleBrand2)
            val getBrandsResponse = mockk<GetBrandsResponse>()

            every { obtainVehicleBrands.execute(VehicleType.CARS) } returns vehicleBrands
            every { vehicleBrandsToGetBrandsResponseConverter.convert(vehicleBrands) } returns getBrandsResponse

            val response = fipeController.getBrands()

            with(response) {
                assertThat(status, equalTo(HttpStatus.OK))
                assertThat(body(), equalTo(getBrandsResponse))
            }

            verify(exactly = 1) { obtainVehicleBrands.execute(VehicleType.CARS) }
            verify(exactly = 1) { vehicleBrandsToGetBrandsResponseConverter.convert(vehicleBrands) }
        }
    }

    describe("#getModels") {

        it("call the dependencies correctly") {
            val brand = VehicleBrand(
                id = 21,
                name = null,
                vehicleType = VehicleType.CARS
            )
            val vehicle1 = mockk<Vehicle>()
            val vehicle2 = mockk<Vehicle>()
            val vehicles = listOf(vehicle1, vehicle2)
            val getModelsResponse = mockk<GetModelsResponse>()

            every { obtainVehicles.execute(brand) } returns vehicles
            every { vehiclesToGetModelsResponseConverter.convert(vehicles) } returns getModelsResponse

            val response = fipeController.getModels(21)

            with(response) {
                assertThat(status, equalTo(HttpStatus.OK))
                assertThat(body(), equalTo(getModelsResponse))
            }

            verify(exactly = 1) { obtainVehicles.execute(brand) }
            verify(exactly = 1) { vehiclesToGetModelsResponseConverter.convert(vehicles) }
        }
    }

    describe("#getDetails") {

        it("call the dependencies correctly") {
            val vehicle = Vehicle(
                id = "437",
                name = null,
                brand = VehicleBrand(
                    id = 21,
                    name = null,
                    vehicleType = VehicleType.CARS
                )
            )
            val vehicleDetails = mockk<VehicleDetails>()
            val getDetailsResponse = mockk<GetDetailsResponse>()

            every { obtainDetails.execute(vehicle) } returns vehicleDetails
            every { vehicleDetailsToGetDetailsResponseConverter.convert(vehicleDetails) } returns getDetailsResponse

            val response = fipeController.getDetails(21, "437")

            with(response) {
                assertThat(status, equalTo(HttpStatus.OK))
                assertThat(body(), equalTo(getDetailsResponse))
            }

            verify(exactly = 1) { obtainDetails.execute(vehicle) }
            verify(exactly = 1) { vehicleDetailsToGetDetailsResponseConverter.convert(vehicleDetails) }
        }
    }
})