package com.ranefare.fipe.core.domains.exceptions

class FipeIntegrationException(message: String?, cause: Throwable?) : Exception(message, cause) {

    constructor() : this(null, null)

    constructor(message: String) : this(message, null)
}