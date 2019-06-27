package com.ranefare.quotation.conf.log

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.*
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.*
import io.reactivex.Flowable
import net.logstash.logback.argument.StructuredArguments.value
import org.reactivestreams.Publisher

@Filter("/**")
class HttpClientLogger : HttpClientFilter, HttpLogger() {

    override fun doFilter(request: HttpRequest<*>?, chain: FilterChain?): Publisher<out HttpResponse<*>> {
        return Flowable.fromCallable { logRequest(request) }
            .switchMap { chain?.proceed(request) }
            .doOnNext { response -> logResponse(response) }
    }

    override fun doFilter(request: MutableHttpRequest<*>?, chain: ClientFilterChain?): Publisher<out HttpResponse<*>> {
        return Flowable.fromCallable { logRequest(request) }
            .switchMap { chain?.proceed(request) }
            .doOnNext { response -> logResponse(response) }
    }

    private fun logRequest(request: HttpRequest<*>?) {
        logRequest("Sent HTTP {} {}", request)
    }

    private fun logResponse(response: HttpResponse<*>?) {
        logResponse("Received HTTP {} response", response)
    }

}