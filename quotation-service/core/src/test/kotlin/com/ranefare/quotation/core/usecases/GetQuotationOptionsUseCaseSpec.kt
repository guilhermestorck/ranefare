package com.ranefare.quotation.core.usecases

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object GetQuotationOptionsUseCaseSpec : Spek({
    
    val useCase by memoized { GetQuotationOptionsUseCase() }

    describe("#execute") {

        it("") {
            
        }
    }

})