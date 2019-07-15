package com.ranefare.quotation.gateways

import com.ranefare.quotation.clients.FipeServiceClient
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object FipeServiceGatewaySpec : Spek({

    val client by memoized { mockk<FipeServiceClient>() }
    val gateway by memoized { FipeServiceGatewayImpl(client) }

    describe("#getDetails") {

        it("") {

        }
    }

})