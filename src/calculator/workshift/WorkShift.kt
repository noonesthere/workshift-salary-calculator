package calculator.workshift

data class WorkShift(
  val startDate: String,
  val beginAt: String,
  val finishedAt: String,
  val bid: Double,
  val workedHours: Double,
  val workShiftSalary: Double,
)
