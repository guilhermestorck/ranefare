package com.ranefare.plancatalogservice.contract.contracts

import com.ranefare.plancatalogservice.contract.domains.GetAllResponse
import com.ranefare.plancatalogservice.contract.domains.resources.InsuranceCoverageItemResource
import io.micronaut.http.HttpResponse

interface GetAllInsuranceCoverageItemsContract {
    fun getAll(): HttpResponse<GetAllResponse<InsuranceCoverageItemResource>>
}