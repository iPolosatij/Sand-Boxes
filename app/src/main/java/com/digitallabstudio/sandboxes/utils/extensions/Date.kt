package com.digitallabstudio.sandboxes.utils.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private const val DATE_FORMAT = "dd.MM.yyyy"
private const val TIME_FORMAT = "HH:mm"
private const val DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm"

fun LocalDate.dateFormat(): String = format(DateTimeFormatter.ofPattern(DATE_FORMAT))

fun LocalDateTime.timeFormat(): String = format(DateTimeFormatter.ofPattern(TIME_FORMAT))

fun ZonedDateTime.dateFormat(): String = format(DateTimeFormatter.ofPattern(DATE_FORMAT))
fun ZonedDateTime.timeFormat(): String = format(DateTimeFormatter.ofPattern(TIME_FORMAT))
fun ZonedDateTime.dateTimeFormat(): String = format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
