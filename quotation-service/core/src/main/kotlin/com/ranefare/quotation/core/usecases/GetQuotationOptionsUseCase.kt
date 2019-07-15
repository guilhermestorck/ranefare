package com.ranefare.quotation.core.usecases

import com.ranefare.quotation.core.domains.CarDescriptor
import com.ranefare.quotation.core.domains.InsuranceCoverageItem
import com.ranefare.quotation.core.domains.InsurancePlan
import com.ranefare.quotation.core.domains.QuotationOption
import com.ranefare.quotation.gateways.FipeServiceGateway
import com.ranefare.quotation.gateways.PlanCatalogGateway

class GetQuotationOptionsUseCase(
    private val planCatalogGateway: PlanCatalogGateway,
    private val fipeServiceGateway: FipeServiceGateway
) {

    fun execute(carDescriptor: CarDescriptor, cpf: String): List<QuotationOption> {
        val price = fipeServiceGateway.getPrice(carDescriptor, cpf)
        val quotationPlans = planCatalogGateway.getPlans()
        val allCoverageItems = planCatalogGateway.getCoverageItems()

        return quotationPlans.map { plan ->
            QuotationOption(
                id = plan.id,
                name = plan.name,
                value = plan.costRate * price,
                coverageItems = allCoverageItems.map { coverageItem ->
                    coverageItem.id to planContainsCoverageItem(plan, coverageItem)
                }.toMap()
            )
        }
    }

    private fun planContainsCoverageItem(plan: InsurancePlan, coverageItem: InsuranceCoverageItem) =
        plan.coverageItems.firstOrNull { it.id == coverageItem.id } != null

}