package com.ranefare.quotation.gateways

import com.ranefare.quotation.clients.InsuranceCoverageItemsCatalogClient
import com.ranefare.quotation.clients.InsurancePlansCatalogClient
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PlanCatalogGatewaySpec : Spek({

    val insurancePlansCatalogClient by memoized { mockk<InsurancePlansCatalogClient>() }
    val insuranceCoverageItemsCatalogClient by memoized { mockk<InsuranceCoverageItemsCatalogClient>() }
    val gateway by memoized { PlanCatalogGatewayImpl(insurancePlansCatalogClient, insuranceCoverageItemsCatalogClient) }

    describe("#getPlans") {

        it("") {

        }
    }

})