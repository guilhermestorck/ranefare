package com.ranefare.fipe.conf

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("com.ranefare.fipe")
            .mainClass(Application.javaClass)
            .start()
    }
}