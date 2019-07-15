package com.ranefare.quotation.controllers

import com.ranefare.quotation.core.usecases.GetQuotationOptionsUseCase
import io.mockk.mockk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object QuotationControllerSpec : Spek({

    val useCase by memoized { mockk<GetQuotationOptionsUseCase>() }
    val controller by memoized {
        QuotationController(useCase)
    }

    describe("#getQuotationOptions") {
        it("") {

        }
    }

})