package com.digitallabstudio.sandboxes.domain.model.exception

import java.io.IOException

sealed class NetworkException : IOException() {
    abstract val traceId: String

    data class Unauthorized(
        override val traceId: String = "Invalid credentials",
    ) : NetworkException()

    data class NotFound(
        override val traceId: String = "Not found",
    ) : NetworkException()

    data class XMPPException(
        var ex: String? = null,
        override val traceId: String = "XMPPException",
    ) : NetworkException()

    data class Conflict(
        override val traceId: String = "Conflict",
    ) : NetworkException()

    data class NoInternetAccess(
        override val traceId: String = "NoInternetAccess",
    ) : NetworkException()

    data class TimeoutError(
        override val traceId: String = " TimeoutError",
    ) : NetworkException()

    data class ParsingError(
        override val traceId: String = "ParsingError",
    ) : NetworkException()

    data class Unknown(
        var ex: String? = null,
        override val traceId: String = "Unknown",
    ) : NetworkException()

    data class ServerError(
        override val traceId: String = "ServerError",
    ) : NetworkException()

    data class BlockDevice(
        override val traceId: String = "BlockDevice",
    ) : NetworkException()
}