package space.dlsunity.arctour.domain.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json: Json by lazy {
    Json {
        isLenient = true
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
    }
}

inline fun <reified T : Any> T.serialize(): String {
    return json.encodeToString(this)
}

inline fun <reified T : Any> String.deserialize(): T {
    return json.decodeFromString(this)
}
