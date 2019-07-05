package steps

import cucumber.api.java.Before
import cucumber.api.java8.Pt

class DevSteps : Pt {

    @Before("@CleanStubby")
    fun cleanupStubby(): Unit {
        stubbyFipeGateway.deleteAllServices()
    }
}