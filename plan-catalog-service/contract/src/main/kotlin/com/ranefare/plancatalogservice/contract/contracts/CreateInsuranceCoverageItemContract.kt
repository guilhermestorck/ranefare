package com.ranefare.plancatalogservice.contract.contracts

import com.ranefare.plancatalogservice.contract.domains.resources.InsuranceCoverageItemResource
import io.micronaut.http.HttpResponse

interface CreateInsuranceCoverageItemContract {
    fun create(resource: InsuranceCoverageItemResource): HttpResponse<InsuranceCoverageItemResource>
}