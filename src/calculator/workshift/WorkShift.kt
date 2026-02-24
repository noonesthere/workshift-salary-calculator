package calculator.workshift

import klite.Decimal
import klite.TSID
import klite.jdbc.BaseEntity
import java.time.LocalDate
import java.time.LocalTime

typealias WorkShiftId = TSID<WorkShift>

data class WorkShift(
  override val id: WorkShiftId = WorkShiftId(),
  val startDate: LocalDate,
  val beginAt: LocalTime,
  val finishedAt: LocalTime,
  val bid: Decimal,
  val workedHours: Decimal,
  val workShiftSalary: Decimal,
) : BaseEntity<WorkShiftId>
