package com.digitallabstudio.sandboxes.data.network.error

import com.digitallabstudio.sandboxes.domain.model.exception.NetworkException
import com.digitallabstudio.sandboxes.utils.extensions.fromJsonOrNull
import com.squareup.moshi.Moshi
import okhttp3.Response
import okhttp3.ResponseBody

class NetworkErrorHandler(
    private val moshi: Moshi,
) {
    private val unknownErrorResponse
        get() = ServerError(
            traceId = "Unknown error"
        )

    fun networkErrorToThrow(url: String, response: Response): NetworkException {
        val serverError = parseErrorBody(response.body)
        return when (response.code) {
            UNAUTHORIZED -> NetworkException.Unauthorized()
            BAD_REQUEST,
            NOT_FOUND,
            in SERVER_ERROR,
            -> NetworkException.ServerError()
            else -> NetworkException.Unknown()
        }
    }

    private fun parseErrorBody(body: ResponseBody?): ServerError {
        return if (body != null) {
            moshi.fromJsonOrNull(ServerError::class, body.string()) ?: unknownErrorResponse
        } else {
            unknownErrorResponse
        }
    }

    companion object {
        private const val BAD_REQUEST = 400
        private const val UNAUTHORIZED = 401
        private const val NOT_FOUND = 404
        private const val BLOCK_DEVICE = 410
        private val SERVER_ERROR = 500..526
    }
}
