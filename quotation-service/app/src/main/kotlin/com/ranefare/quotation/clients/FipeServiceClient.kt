package com.ranefare.quotation.clients

import com.ranefare.fipe.contract.contracts.GetDetailsContract
import com.ranefare.fipe.contract.domains.getDetails.GetDetailsResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client(id = "fipe-service", path = "/fipe")
interface FipeServiceClient : GetDetailsContract {

    @Get("/brands/{brandId}/models/{modelId}/details")
    override fun getDetails(brandId: Int, modelId: String): HttpResponse<GetDetailsResponse>
}