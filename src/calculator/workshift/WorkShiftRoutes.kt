package calculator.workshift

import klite.annotations.POST

class WorkShiftRoutes {

  @POST
  fun save(workshift: WorkShift): WorkShift {
    return workshift
  }
}
