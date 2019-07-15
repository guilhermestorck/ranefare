package com.ranefare.quotation.gateways

import com.ranefare.quotation.clients.FipeServiceClient
import com.ranefare.quotation.core.domains.CarDescriptor
import javax.inject.Singleton

@Singleton
class FipeServiceGatewayImpl(private val fipeServiceClient: FipeServiceClient) : FipeServiceGateway {

    override fun getPrice(carDescriptor: CarDescriptor, cpf: String): Double {
        return fipeServiceClient.getDetails(carDescriptor.carBrand, carDescriptor.carModel).body()!!.price.toDouble()
    }

}