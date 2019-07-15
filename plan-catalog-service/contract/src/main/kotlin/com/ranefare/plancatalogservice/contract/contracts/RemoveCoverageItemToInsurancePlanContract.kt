package com.ranefare.plancatalogservice.contract.contracts

import com.ranefare.plancatalogservice.contract.domains.resources.InsurancePlanResource
import io.micronaut.http.HttpResponse

interface RemoveCoverageItemToInsurancePlanContract {

    fun removeCoverageItem(planId: String, coverageItemId: String): HttpResponse<InsurancePlanResource>
}