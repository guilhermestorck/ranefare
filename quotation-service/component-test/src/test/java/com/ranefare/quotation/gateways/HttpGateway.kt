package com.ranefare.quotation.gateways

import com.ranefare.quotation.conf.Hosts
import cucumber.api.DataTable
import khttp.responses.Response
import parsers.ApiDataTableParser

object HttpGateway {

    private val APIS = mapOf(
        
    )

    fun request(apiName: String, dataTable: DataTable): Response {

        val requestDataTable =
            ApiDataTableParser.parseRequestDataTable("${Hosts.APP.address()}${APIS[apiName]}", apiName, dataTable)

        return khttp.request(
            method = requestDataTable.method,
            url = requestDataTable.url,
            headers = requestDataTable.headers,
            json = requestDataTable.body,
            params = requestDataTable.params
        )
    }
}