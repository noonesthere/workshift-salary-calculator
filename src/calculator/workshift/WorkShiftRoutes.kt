package calculator.workshift

import klite.annotations.GET
import klite.annotations.PATCH
import klite.annotations.POST
import klite.annotations.PathParam
import klite.annotations.QueryParam
import java.time.LocalDate

class WorkShiftRoutes(private val repo: WorkShiftRepository) {

  @POST
  fun save(workshift: WorkShift)= workshift.also { repo.save(it) }

  @GET
  fun get(
    @QueryParam("from") from: LocalDate,
    @QueryParam("to") to: LocalDate
  ) = repo.listByRange(from, to)


@PATCH("/:id")
fun patch(@PathParam("id") id: WorkShiftId, body: PatchBody): WorkShift = repo.get(id)
  .copy(included = body.included)
  .also(repo::save)
}

data class PatchBody(val included: Boolean)
