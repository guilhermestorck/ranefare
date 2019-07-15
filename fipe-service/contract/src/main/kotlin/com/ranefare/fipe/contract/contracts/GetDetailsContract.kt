package com.ranefare.fipe.contract.contracts

import com.ranefare.fipe.contract.domains.getDetails.GetDetailsResponse
import io.micronaut.http.HttpResponse

interface GetDetailsContract {
    fun getDetails(brandId: Int, modelId: String): HttpResponse<GetDetailsResponse>
}
