package calculator.workshift

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream

class WorkShiftDocumentExporter {

  fun export(items: List<WorkShift>): ByteArray {
    return XSSFWorkbook().use { workbook ->
      val sheet = workbook.createSheet("Робочі зміни")

      val headerStyle = createHeaderCellStyle(workbook)

      buildHeader(sheet, headerStyle)
      buildBody(sheet, items)
      for (i in 0..5) {
        sheet.autoSizeColumn(i)
      }

      ByteArrayOutputStream().use { output ->
        workbook.write(output)
        output.toByteArray()
      }
    }
  }

  private fun createHeaderCellStyle(workbook: XSSFWorkbook): CellStyle {
    val headerFont = workbook.createFont().apply {
      fontHeightInPoints = 14
      bold = true
    }

    val headerStyle = workbook.createCellStyle().apply {
      setFont(headerFont)
      alignment = HorizontalAlignment.CENTER
      verticalAlignment = VerticalAlignment.CENTER

      fillForegroundColor = IndexedColors.AQUA.index
      fillPattern = FillPatternType.SOLID_FOREGROUND
    }
    return headerStyle
  }

  private fun buildHeader(sheet: Sheet, cellStyle: CellStyle) = sheet.row(0) {
    cell(0, cellStyle) { setCellValue("Дата") }
    cell(1, cellStyle) { setCellValue("Початок зміни") }
    cell(2, cellStyle) { setCellValue("Час завершення") }
    cell(3, cellStyle) { setCellValue("Ставка(грн/год)") }
    cell(4, cellStyle) { setCellValue("Відпрацьовано(год)") }
    cell(5, cellStyle) { setCellValue("Зароблено за зміну(грн)") }
  }

  private fun buildBody(sheet: Sheet, items: List<WorkShift>) {
    items.forEachIndexed { index, shift ->
      sheet.row(index + 1) {
        cell(0) { setCellValue(shift.startDate.toString()) }
        cell(1) { setCellValue(shift.beginAt.toString()) }
        cell(2) { setCellValue(shift.finishedAt.toString()) }
        cell(3) { setCellValue(shift.bid.toString()) }
        cell(4) { setCellValue(shift.workedHours.toString()) }
        cell(5) { setCellValue(shift.workShiftSalary.toString()) }
      }
    }
  }

  private fun Sheet.row(index: Int, block: Row.() -> Unit) {
    createRow(index).block()
  }

  private fun Row.cell(
    index: Int,
    cellStyle: CellStyle? = null,
    block: Cell.() -> Unit
  ) {
    val cell = createCell(index)
    cell.block()
    cellStyle?.let { cell.cellStyle = it }
  }
}
