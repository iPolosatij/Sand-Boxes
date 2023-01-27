package com.digitallabstudio.sandboxes.data.network.api.converters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateTimeAdapter {

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    }

    @FromJson
    fun fromJson(value: Any): ZonedDateTime {
        return when (value) {
            is String -> ZonedDateTime.parse(value, FORMATTER)
                .withZoneSameInstant(ZoneId.systemDefault())
            is Number -> ZonedDateTime.from(
                Instant.ofEpochMilli(value.toLong()).atZone(ZoneId.systemDefault())
            )
            else -> ZonedDateTime.now(ZoneId.systemDefault())
        }
    }

    @ToJson
    fun toJson(value: ZonedDateTime): String {
        return FORMATTER.format(value)
    }

    @ToJson
    fun toJson(localDate: LocalDate): Long {
        return localDate.toEpochDay()
    }
}