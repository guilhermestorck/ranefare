package com.ranefare.quotation.gateways

import com.ranefare.quotation.core.domains.InsuranceCoverageItem
import com.ranefare.quotation.core.domains.InsurancePlan

interface PlanCatalogGateway {

    fun getPlans(): List<InsurancePlan>

    fun getCoverageItems(): List<InsuranceCoverageItem>

}