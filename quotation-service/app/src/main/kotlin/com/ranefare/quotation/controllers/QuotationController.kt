package com.ranefare.quotation.controllers

import com.ranefare.quotation.contract.contracts.GetQuotationOptionsContract
import com.ranefare.quotation.contract.domains.getquotationplans.GetQuotationOptionsRequest
import com.ranefare.quotation.contract.domains.getquotationplans.GetQuotationOptionsResponse
import com.ranefare.quotation.contract.domains.getquotationplans.QuotationOptionResponse
import com.ranefare.quotation.core.domains.CarDescriptor
import com.ranefare.quotation.core.usecases.GetQuotationOptionsUseCase
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/quotation")
class QuotationController @Inject constructor(
    private val getQuotationOptionsUseCase: GetQuotationOptionsUseCase
) : GetQuotationOptionsContract {

    @Post("/options")
    override fun getQuotationOptions(
        @Body body: GetQuotationOptionsRequest
    ): HttpResponse<GetQuotationOptionsResponse> {
        return HttpResponse.ok(
            GetQuotationOptionsResponse(
                options = getQuotationOptionsUseCase.execute(
                    cpf = body.cpf,
                    carDescriptor = CarDescriptor(
                        carBrand = body.carBrand,
                        carModel = body.carModel,
                        carYear = body.carYear,
                        zeroKm = body.zeroKm
                    )
                ).map {
                    QuotationOptionResponse(
                        id = it.id,
                        name = it.name,
                        value = it.value,
                        coverageItems = it.coverageItems
                    )
                }
            )
        )
    }
}