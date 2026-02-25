package calculator.workshift

import klite.annotations.GET
import klite.annotations.POST

class WorkShiftRoutes(private val workShiftRepository: WorkShiftRepository) {

  @POST
  fun save(workshift: WorkShift): WorkShift = workshift.also { workShiftRepository.save(it) }

  @GET
  fun get(): List<WorkShift> = workShiftRepository.list()
}
