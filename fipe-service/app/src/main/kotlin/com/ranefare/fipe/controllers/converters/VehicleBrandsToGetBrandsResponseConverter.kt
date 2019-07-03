package com.ranefare.fipe.controllers.converters

import com.ranefare.fipe.contract.domains.getBrands.BrandResponse
import com.ranefare.fipe.contract.domains.getBrands.GetBrandsResponse
import com.ranefare.fipe.core.domains.VehicleBrand
import javax.inject.Singleton

@Singleton
class VehicleBrandsToGetBrandsResponseConverter {
    fun convert(brands: List<VehicleBrand>): GetBrandsResponse = GetBrandsResponse(
        brands = brands.map { brand ->
            BrandResponse(
                brandId = brand.id,
                brandName = brand.name!!
            )
        }
    )
}