package calculator.workshift

import klite.HttpExchange
import klite.StatusCode
import klite.annotations.GET
import klite.annotations.QueryParam
import java.net.URLEncoder
import java.time.LocalDate

private const val EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"

class ExportRoutes(
  private val repo: WorkShiftRepository,
  private val exporter: WorkShiftDocumentExporter
) {
  @GET
  fun export(
    @QueryParam("from") from: LocalDate,
    @QueryParam("to") to: LocalDate,
    e: HttpExchange
  ) {
    val items = repo.listByRange(from, to).filter { it.included }
    val bytes = exporter.export(items)
    val fileName = "Звіт_з_${from}_по_${to}.xlsx"
    val encodedFileName = URLEncoder.encode(fileName, Charsets.UTF_8)

    e.header(
      "Content-Disposition",
      "attachment; filename=\"report.xlsx\"; filename*=UTF-8''$encodedFileName"
    )
    e.send(StatusCode.OK, bytes, EXCEL_CONTENT_TYPE)
  }
}
