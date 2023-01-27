package space.dlsunity.arctour.data.network.api.converters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.*
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    }

    @FromJson
    fun fromJson(value: Any): LocalDateTime {
        return when (value) {
            is String -> LocalDateTime.from(FORMATTER.parse(value))
            is Number -> Instant.ofEpochMilli(value.toLong()).atZone(ZoneId.systemDefault()).toLocalDateTime()
            else -> LocalDateTime.now()
        }
    }

    @ToJson
    fun toJson(value: LocalDateTime): String {
        val offsetDateTime = OffsetDateTime.of(value, ZoneOffset.UTC)
        return FORMATTER.format(offsetDateTime)
    }

    @ToJson
    fun toJson(localDate: LocalDate): Long {
        return localDate.toEpochDay()
    }
}
