package com.ranefare.quotation.contract.contracts

import com.ranefare.quotation.contract.domains.getquotationplans.GetQuotationOptionsRequest
import com.ranefare.quotation.contract.domains.getquotationplans.GetQuotationOptionsResponse
import io.micronaut.http.HttpResponse

interface GetQuotationOptionsContract {

    fun getQuotationOptions(body: GetQuotationOptionsRequest): HttpResponse<GetQuotationOptionsResponse>

}