package calculator.workshift

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun LocalDate.asString() = DATE_TIME_FORMATTER.format(this)
