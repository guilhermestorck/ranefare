package com.ranefare.quotation.contract.contracts

import com.ranefare.quotation.contract.domains.getplans.GetPlansRequest
import com.ranefare.quotation.contract.domains.getplans.GetPlansResponse
import io.micronaut.http.HttpResponse

interface QuotationContract {

    fun getPlans(body: GetPlansRequest): HttpResponse<GetPlansResponse>
}
