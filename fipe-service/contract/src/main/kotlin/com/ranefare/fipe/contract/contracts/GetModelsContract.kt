package com.ranefare.fipe.contract.contracts

import com.ranefare.fipe.contract.domains.getModels.GetModelsResponse
import io.micronaut.http.HttpResponse

interface GetModelsContract {
    fun getModels(brandId: Int): HttpResponse<GetModelsResponse>
}
