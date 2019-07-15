package com.ranefare.quotation.clients

import com.ranefare.plancatalogservice.contract.contracts.GetAllInsurancePlansContract
import com.ranefare.plancatalogservice.contract.domains.GetAllResponse
import com.ranefare.plancatalogservice.contract.domains.resources.InsurancePlanResource
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client(id = "plan-catalog-service", path = "/insurance/plans")
interface InsurancePlansCatalogClient : GetAllInsurancePlansContract {

    @Get
    override fun getAll(): HttpResponse<GetAllResponse<InsurancePlanResource>>
}