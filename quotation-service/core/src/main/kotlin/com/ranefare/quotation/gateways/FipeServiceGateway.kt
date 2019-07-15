package com.ranefare.quotation.gateways

import com.ranefare.quotation.core.domains.CarDescriptor

interface FipeServiceGateway {

    fun getPrice(carDescriptor: CarDescriptor, cpf: String): Double

}