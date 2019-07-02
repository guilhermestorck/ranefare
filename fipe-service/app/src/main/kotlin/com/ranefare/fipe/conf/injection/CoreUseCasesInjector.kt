package com.ranefare.fipe.conf.injection

import com.ranefare.fipe.core.gateways.FipeGateway
import com.ranefare.fipe.core.usecase.ObtainDetails
import com.ranefare.fipe.core.usecase.ObtainVehicleBrands
import com.ranefare.fipe.core.usecase.ObtainVehicles
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

@Factory
class CoreUseCasesInjector {
 
    @Singleton
    fun injectObtainVehicleBrands(fipeGateway: FipeGateway): ObtainVehicleBrands {
        return ObtainVehicleBrands(fipeGateway)
    }

    @Singleton
    fun injectObtainVehicles(fipeGateway: FipeGateway): ObtainVehicles {
        return ObtainVehicles(fipeGateway)
    }

    @Singleton
    fun injectObtainDetails(fipeGateway: FipeGateway): ObtainDetails {
        return ObtainDetails(fipeGateway)
    }
}