package com.ranefare.plancatalogservice.contract.contracts

import com.ranefare.plancatalogservice.contract.domains.resources.InsurancePlanResource
import io.micronaut.http.HttpResponse

interface CreateInsurancePlanContract {
    fun create(resource: InsurancePlanResource): HttpResponse<InsurancePlanResource>
}