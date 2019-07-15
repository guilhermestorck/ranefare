package com.ranefare.plancatalogservice.contract.contracts

import com.ranefare.plancatalogservice.contract.domains.resources.InsuranceCoverageItemResource
import io.micronaut.http.HttpResponse

interface GetInsuranceCoverageItemContract {
    fun get(id: String): HttpResponse<InsuranceCoverageItemResource>
}