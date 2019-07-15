package com.ranefare.fipe.contract.contracts

import com.ranefare.fipe.contract.domains.getBrands.GetBrandsResponse
import io.micronaut.http.HttpResponse

interface GetBrandsContract {
    fun getBrands(): HttpResponse<GetBrandsResponse>
}
