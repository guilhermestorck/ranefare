package com.ranefare.quotation.gateways

import com.ranefare.quotation.clients.InsuranceCoverageItemsCatalogClient
import com.ranefare.quotation.clients.InsurancePlansCatalogClient
import com.ranefare.quotation.core.domains.InsuranceCoverageItem
import com.ranefare.quotation.core.domains.InsurancePlan
import javax.inject.Singleton

@Singleton
class PlanCatalogGatewayImpl(
    private val insurancePlansCatalogClient: InsurancePlansCatalogClient,
    private val insuranceCoverageItemsCatalogClient: InsuranceCoverageItemsCatalogClient
) : PlanCatalogGateway {

    override fun getPlans(): List<InsurancePlan> {
        return insurancePlansCatalogClient.getAll().body()!!.items.map {
            InsurancePlan(
                id = it.id!!,
                name = it.name,
                costRate = it.costRate,
                coverageItems = it.coverageItems!!.map { item ->
                    InsuranceCoverageItem(
                        id = item.id!!,
                        name = item.name,
                        description = item.description
                    )
                }
            )
        }
    }

    override fun getCoverageItems(): List<InsuranceCoverageItem> {
        return insuranceCoverageItemsCatalogClient.getAll().body()!!.items.map {
            InsuranceCoverageItem(
                id = it.id!!,
                name = it.name,
                description = it.description
            )
        }
    }

}