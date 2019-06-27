package com.ranefare.quotation.controllers

import com.ranefare.quotation.contract.contracts.QuotationContract
import com.ranefare.quotation.contract.domains.getplans.GetPlansRequest
import com.ranefare.quotation.contract.domains.getplans.GetPlansResponse
import com.ranefare.quotation.contract.domains.getplans.QuotationOptionResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

@Controller("/quotation")
class QuotationController : QuotationContract {

    @Post("/plans")
    override fun getPlans(body: GetPlansRequest): HttpResponse<GetPlansResponse> {
        return HttpResponse.ok(
            GetPlansResponse(
                plans = listOf(
                    QuotationOptionResponse(
                        name = "SIMPLES",
                        price = 8498.0,
                        coverageItems = mapOf(
                            "ROUBO" to true,
                            "CHUVA" to false
                        )
                    ),
                    QuotationOptionResponse(
                        name = "COMPLETO",
                        price = 8490238.0,
                        coverageItems = mapOf(
                            "ROUBO" to true,
                            "CHUVA" to true
                        )
                    )
                )
            )
        )
    }

    @Get("/")
    fun test(): String {
        return "hello world"
    }
}