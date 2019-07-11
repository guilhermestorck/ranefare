package com.ranefare.fipe.controllers

import com.ranefare.fipe.contract.domains.ExceptionResponse
import com.ranefare.fipe.core.domains.exceptions.FipeIntegrationException
import io.micronaut.http.HttpStatus
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ExceptionHandlerSpec : Spek({
    val exceptionHandler by memoized { ExceptionHandler() }

    describe("#handleFipeIntegrationException}") {

        it("return the response correctly when the exception has a message") {
            val response = exceptionHandler.handleFipeIntegrationException(
                FipeIntegrationException(message = "Empty result.")
            )

            with(response) {
                MatcherAssert.assertThat(status, CoreMatchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR))
                MatcherAssert.assertThat(body(), CoreMatchers.equalTo(ExceptionResponse("Integration exception: Empty result.")))
            }
        }

        it("return the response correctly when the exception hasn't a message") {
            val response = exceptionHandler.handleFipeIntegrationException(FipeIntegrationException())

            with(response) {
                MatcherAssert.assertThat(status, CoreMatchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR))
                MatcherAssert.assertThat(body(), CoreMatchers.equalTo(ExceptionResponse("Integration exception.")))
            }
        }
    }
})