package calculator.workshift

import klite.annotations.GET
import klite.annotations.POST
import klite.annotations.QueryParam
import java.time.LocalDate

class WorkShiftRoutes(private val repo: WorkShiftRepository) {

  @POST
  fun save(workshift: WorkShift): WorkShift = workshift.also { repo.save(it) }

  @GET
  fun get(@QueryParam("from") from: LocalDate, @QueryParam("to") to: LocalDate) = repo.listByRange(from, to)
}
