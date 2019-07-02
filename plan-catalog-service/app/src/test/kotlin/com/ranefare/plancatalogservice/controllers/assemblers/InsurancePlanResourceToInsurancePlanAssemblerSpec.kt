package com.ranefare.plancatalogservice.controllers.assemblers

import com.ranefare.plancatalogservice.contract.domains.resources.InsurancePlanResource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class InsurancePlanResourceToInsurancePlanAssemblerSpec : Spek({

    val itemsAssembler by memoized { mockk<InsuranceCoverageItemResourceToInsuranceCoverageItemAssembler>() }
    val assembler by memoized { InsurancePlanResourceToInsurancePlanAssembler(itemsAssembler) }

    describe("#assemble") {

        val resource by memoized {
            InsurancePlanResource(
                name = "name",
                costRate = 0.05,
                id = "id",
                coverageItems = listOf(
                    mockk(), mockk()
                )
            )
        }

        it("should assemble a plan") {
            every { itemsAssembler.assemble(any()) } returns mockk()

            val result = assembler.assemble(resource)

            with(result) {
                assertEquals(name, "name")
                assertEquals(costRate, 0.05)
                assertEquals(id, "id")
                assertEquals(coverageItems.size, 2)
            }
            verify(exactly = 2) { itemsAssembler.assemble(any()) }
        }

        it("should assemble a plan with a null id") {
            every { itemsAssembler.assemble(any()) } returns mockk()

            val result = assembler.assemble(resource.copy(id = null))

            with(result) {
                assertEquals(name, "name")
                assertEquals(costRate, 0.05)
                assertEquals(id, null)
                assertEquals(coverageItems.size, 2)
            }
            verify(exactly = 2) { itemsAssembler.assemble(any()) }
        }

        it("should assemble a plan without coverage items") {
            every { itemsAssembler.assemble(any()) } returns mockk()

            val result = assembler.assemble(resource.copy(coverageItems = null))

            with(result) {
                assertEquals(name, "name")
                assertEquals(costRate, 0.05)
                assertEquals(id, "id")
                assertEquals(coverageItems.size, 0)
            }
            verify(exactly = 0) { itemsAssembler.assemble(any()) }
        }

    }
})