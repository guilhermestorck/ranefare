package com.ranefare.plancatalogservice.controllers

import com.ranefare.plancatalogservice.contract.contracts.AddCoverageItemToInsurancePlanContract
import com.ranefare.plancatalogservice.contract.contracts.CreateInsurancePlanContract
import com.ranefare.plancatalogservice.contract.contracts.GetAllInsurancePlansContract
import com.ranefare.plancatalogservice.contract.contracts.GetInsurancePlanContract
import com.ranefare.plancatalogservice.contract.contracts.RemoveCoverageItemToInsurancePlanContract
import com.ranefare.plancatalogservice.contract.domains.GetAllResponse
import com.ranefare.plancatalogservice.contract.domains.resources.InsurancePlanResource
import com.ranefare.plancatalogservice.controllers.assemblers.InsurancePlanResourceToInsurancePlanAssembler
import com.ranefare.plancatalogservice.controllers.converters.InsurancePlanToInsurancePlanResourceConverter
import com.ranefare.plancatalogservice.core.usecases.insuranceplan.AddCoverageItemToInsurancePlanUseCase
import com.ranefare.plancatalogservice.core.usecases.insuranceplan.CreateInsurancePlanUseCase
import com.ranefare.plancatalogservice.core.usecases.insuranceplan.GetAllInsurancePlansUseCase
import com.ranefare.plancatalogservice.core.usecases.insuranceplan.GetInsurancePlanUseCase
import com.ranefare.plancatalogservice.core.usecases.insuranceplan.RemoveCoverageItemFromInsurancePlanUseCase
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import javax.inject.Inject

@Controller("/insurance/plans")
class InsurancePlanCrudController @Inject constructor(
    private val createUseCase: CreateInsurancePlanUseCase,
    private val getUseCase: GetInsurancePlanUseCase,
    private val getAllUseCase: GetAllInsurancePlansUseCase,
    private val addCoverageItemToInsurancePlanUseCase: AddCoverageItemToInsurancePlanUseCase,
    private val removeCoverageItemFromInsurancePlanUseCase: RemoveCoverageItemFromInsurancePlanUseCase,
    private val resourceConverter: InsurancePlanToInsurancePlanResourceConverter,
    private val resourceAssembler: InsurancePlanResourceToInsurancePlanAssembler
) : CreateInsurancePlanContract,
    GetInsurancePlanContract,
    GetAllInsurancePlansContract,
    AddCoverageItemToInsurancePlanContract,
    RemoveCoverageItemToInsurancePlanContract {

    @Post
    override fun create(@Body resource: InsurancePlanResource): HttpResponse<InsurancePlanResource> {
        val created = createUseCase.execute(resourceAssembler.assemble(resource))
        return HttpResponse.created(resourceConverter.convert(created))
    }

    @Get("/{id}")
    override fun get(@PathVariable("id") id: String): HttpResponse<InsurancePlanResource> {
        return HttpResponse.ok(resourceConverter.convert(getUseCase.execute(id)))
    }

    @Get
    override fun getAll(): HttpResponse<GetAllResponse<InsurancePlanResource>> {
        return HttpResponse.ok(
            GetAllResponse(
                items = getAllUseCase.execute().map(resourceConverter::convert)
            )
        )
    }

    @Post("/{planId}/coverage/{coverageItemId}")
    override fun addCoverageItem(
        @PathVariable("planId") planId: String,
        @PathVariable("coverageItemId") coverageItemId: String
    ): HttpResponse<InsurancePlanResource> {
        val plan = addCoverageItemToInsurancePlanUseCase.execute(planId, coverageItemId)
        return HttpResponse.ok(
            resourceConverter.convert(plan)
        )
    }

    @Delete("/{planId}/coverage/{coverageItemId}")
    override fun removeCoverageItem(
        @PathVariable("planId") planId: String,
        @PathVariable("coverageItemId") coverageItemId: String
    ): HttpResponse<InsurancePlanResource> {
        val plan = removeCoverageItemFromInsurancePlanUseCase.execute(planId, coverageItemId)
        return HttpResponse.ok(
            resourceConverter.convert(plan)
        )
    }
}