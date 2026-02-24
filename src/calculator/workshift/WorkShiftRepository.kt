package calculator.workshift

import klite.jdbc.BaseCrudRepository
import klite.jdbc.insert
import klite.toValues
import javax.sql.DataSource


class WorkShiftRepository(db: DataSource) : BaseCrudRepository<WorkShift, WorkShiftId>(db, "work_shift") {

  fun store(workShift: WorkShift) {
    db.insert(table, workShift.toValues())
  }
}
