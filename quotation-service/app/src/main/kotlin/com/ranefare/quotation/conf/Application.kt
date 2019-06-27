package com.ranefare.quotation.conf

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("com.ranefare.quotation")
            .mainClass(Application.javaClass)
            .start()
    }
}