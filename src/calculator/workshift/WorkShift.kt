package calculator.workshift

import klite.Decimal
import klite.TSID
import klite.jdbc.BaseEntity
import klite.jdbc.Column
import java.time.LocalDate
import java.time.LocalTime

typealias WorkShiftId = TSID<WorkShift>

data class WorkShift(
  @Column("id") override val id: WorkShiftId = WorkShiftId(),
  @Column("start_date") val startDate: LocalDate,
  @Column("begin_at") val beginAt: LocalTime,
  @Column("finished_at") val finishedAt: LocalTime,
  @Column("bid") val bid: Decimal,
  @Column("worked_hours") val workedHours: Decimal,
  @Column("work_shift_salary") val workShiftSalary: Decimal,
  @Column("included") val included: Boolean = true,
) : BaseEntity<WorkShiftId>
