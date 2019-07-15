package com.ranefare.quotation.clients

import com.ranefare.plancatalogservice.contract.contracts.GetAllInsuranceCoverageItemsContract
import com.ranefare.plancatalogservice.contract.domains.GetAllResponse
import com.ranefare.plancatalogservice.contract.domains.resources.InsuranceCoverageItemResource
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client(id = "plan-catalog-service", path = "/insurance/coverage-items")
interface InsuranceCoverageItemsCatalogClient : GetAllInsuranceCoverageItemsContract {

    @Get
    override fun getAll(): HttpResponse<GetAllResponse<InsuranceCoverageItemResource>>
}