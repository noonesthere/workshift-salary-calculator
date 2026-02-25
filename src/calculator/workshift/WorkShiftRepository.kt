package calculator.workshift

import klite.jdbc.BaseCrudRepository
import javax.sql.DataSource


class WorkShiftRepository(db: DataSource) : BaseCrudRepository<WorkShift, WorkShiftId>(db, "work_shift") {
  override val orderAsc get() = "order by start_date"
}
