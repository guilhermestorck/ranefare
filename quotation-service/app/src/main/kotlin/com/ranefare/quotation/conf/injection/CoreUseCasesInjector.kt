package com.ranefare.quotation.conf.injection

import com.ranefare.quotation.core.usecases.GetQuotationOptionsUseCase
import com.ranefare.quotation.gateways.FipeServiceGateway
import com.ranefare.quotation.gateways.PlanCatalogGateway
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

@Factory
class CoreUseCasesInjector {

    @Singleton
    fun getQuotationOptionsUseCase(
        planCatalogGateway: PlanCatalogGateway, fipeServiceGateway: FipeServiceGateway
    ): GetQuotationOptionsUseCase {
        return GetQuotationOptionsUseCase(planCatalogGateway, fipeServiceGateway)
    }
}