package com.ranefare.plancatalogservice.contract.contracts

import com.ranefare.plancatalogservice.contract.domains.GetAllResponse
import com.ranefare.plancatalogservice.contract.domains.resources.InsurancePlanResource
import io.micronaut.http.HttpResponse

interface GetAllInsurancePlansContract {
    fun getAll(): HttpResponse<GetAllResponse<InsurancePlanResource>>
}