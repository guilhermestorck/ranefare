package steps

import cucumber.api.java.Before
import cucumber.api.java8.Pt
import gateways.StubbyGateway

class DevSteps : Pt {

    @Before("@CleanStubby")
    fun cleanupStubby() {
        StubbyGateway.deleteAllServices()
    }
}