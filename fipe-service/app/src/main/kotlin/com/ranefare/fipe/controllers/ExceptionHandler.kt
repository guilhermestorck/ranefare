package com.ranefare.fipe.controllers

import com.ranefare.fipe.contract.domains.ExceptionResponse
import com.ranefare.fipe.core.domains.exceptions.FipeIntegrationException
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error

@Controller
class ExceptionHandler {

    @Error(global = true, exception = FipeIntegrationException::class)
    fun handleFipeIntegrationException(exception: FipeIntegrationException): HttpResponse<ExceptionResponse> =
        HttpResponse.serverError(
            ExceptionResponse(
                if (exception.message != null) "Integration exception: ${exception.message}"
                else "Integration exception."
            )
        )
}