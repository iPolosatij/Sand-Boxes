package com.digitallabstudio.sandboxes.data.network.error

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ServerError(
    val traceId: String,
)
