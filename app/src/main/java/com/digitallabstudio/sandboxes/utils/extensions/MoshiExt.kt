package com.digitallabstudio.sandboxes.utils.extensions

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import kotlin.reflect.KClass

fun <T : Any> Moshi.fromJsonOrNull(type: KClass<T>, content: String): T? {
    return try {
        adapter(type.java).fromJson(content)
    } catch (ex: JsonDataException) {
        null
    }
}

@Throws(AssertionError::class)
fun <T : Any> Moshi.toJsonOrThrow(type: KClass<T>, content: T): String? =
    adapter(type.java).toJson(content)