package calculator.workshift

import klite.annotations.POST

class WorkShiftRoutes(private val workShiftRepository: WorkShiftRepository) {

  @POST
  fun save(workshift: WorkShift): WorkShift = workshift.also { workShiftRepository.save(it) }
}
