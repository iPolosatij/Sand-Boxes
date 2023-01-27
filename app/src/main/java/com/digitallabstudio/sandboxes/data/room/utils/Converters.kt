package com.digitallabstudio.sandboxes.data.room.utils

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)
    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromBitmap(value : ByteArray) = Json.encodeToString(value)
    @TypeConverter
    fun toBitmap(value: String) = Json.decodeFromString<ByteArray>(value)

}