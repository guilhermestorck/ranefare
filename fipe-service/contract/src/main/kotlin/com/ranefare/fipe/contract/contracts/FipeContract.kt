package com.ranefare.fipe.contract.contracts

import com.ranefare.fipe.contract.domains.getBrands.GetBrandsResponse
import com.ranefare.fipe.contract.domains.getDetails.GetDetailsResponse
import com.ranefare.fipe.contract.domains.getModels.GetModelsResponse
import io.micronaut.http.HttpResponse

interface FipeContract {
    fun getBrands(): HttpResponse<GetBrandsResponse>

    fun getModels(brandId: Int): HttpResponse<GetModelsResponse>

    fun getDetails(brandId: Int, modelId: String): HttpResponse<GetDetailsResponse>
}
