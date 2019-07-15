package com.ranefare.plancatalogservice.contract.contracts

import com.ranefare.plancatalogservice.contract.domains.resources.InsurancePlanResource
import io.micronaut.http.HttpResponse

interface GetInsurancePlanContract {
    fun get(id: String): HttpResponse<InsurancePlanResource>
}