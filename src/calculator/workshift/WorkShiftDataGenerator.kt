package calculator.workshift

import klite.Decimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

object WorkShiftDataGenerator {

  private val startDate = LocalDate.of(2021, 1, 1)
  private val endDate = LocalDate.of(2027, 12, 31)
  private val totalDays = ChronoUnit.DAYS.between(startDate, endDate).toInt()

  private val random = Random(System.currentTimeMillis())

  fun generate(count: Int = 1_000_000): Sequence<WorkShift> = sequence {
    repeat(count) {
      yield(randomShift())
    }
  }

  private fun randomShift(): WorkShift {
    val date = startDate.plusDays(random.nextInt(totalDays).toLong())

    val beginHour = random.nextInt(6, 12) // between 06:00 and 11:00
    val durationHours = random.nextInt(4, 13) // 4 to 12 hours

    val beginAt = LocalTime.of(beginHour, listOf(0, 15, 30, 45).random())
    val finishedAt = beginAt.plusHours(durationHours.toLong())

    val workedHours = Decimal(durationHours.toString())

    val bidValue = random.nextInt(80, 251) // 80–250 per hour
    val bid = Decimal(bidValue.toString())

    val salary = Decimal((durationHours * bidValue).toString())

    return WorkShift(
      startDate = date,
      beginAt = beginAt,
      finishedAt = finishedAt,
      bid = bid,
      workedHours = workedHours,
      workShiftSalary = salary,
      included = true
    )
  }
}
