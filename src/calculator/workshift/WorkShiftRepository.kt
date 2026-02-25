package calculator.workshift

import klite.jdbc.BaseCrudRepository
import klite.jdbc.gte
import klite.jdbc.lte
import klite.jdbc.select
import java.time.LocalDate
import javax.sql.DataSource


class WorkShiftRepository(db: DataSource) : BaseCrudRepository<WorkShift, WorkShiftId>(db, "work_shift") {
  override val orderAsc get() = "order by start_date"

  fun listByRange(from: LocalDate, to: LocalDate): List<WorkShift> {
    return db.select(table, WorkShift::startDate gte from, WorkShift::startDate lte to)
  }
}
