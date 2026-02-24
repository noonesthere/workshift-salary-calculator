package calculator.workshift

import klite.annotations.GET
import klite.annotations.POST

class WorkShiftRoutes(private val workShiftRepository: WorkShiftRepository) {

  @POST
  fun save(workshift: WorkShift): WorkShift = workshift.also { workShiftRepository.save(it) }

  @GET
  fun all(): List<WorkShift> = workShiftRepository.list(suffix = "order by start_date desc")
}
