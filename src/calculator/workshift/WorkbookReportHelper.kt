package calculator.workshift

import klite.sumOf
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook

fun Sheet.row(index: Int, block: Row.() -> Unit) {
  createRow(index).block()
}


fun baseCellStyle(workbook: XSSFWorkbook): CellStyle {
  val baseFont = workbook.createFont().apply {
    fontHeightInPoints = 12
  }

  return workbook.createCellStyle().apply {
    setFont(baseFont)

    alignment = HorizontalAlignment.RIGHT
    verticalAlignment = VerticalAlignment.JUSTIFY

    borderTop = BorderStyle.THIN
    borderBottom = BorderStyle.THIN
    borderLeft = BorderStyle.THIN
    borderRight = BorderStyle.THIN
  }
}

private const val TITLE_ROW_INDEX = 0
private const val TITLE_CELL_INDEX = 0

private const val TOTAL_HOURS_ROW_INDEX = 3
private const val TOTAL_HOURS_TITLE_CELL_INDEX = 0
private const val TOTAL_HOURS_VALUE_CELL_INDEX = 1

private const val TOTAL_SALARY_ROW_INDEX = 4
private const val TOTAL_SALARY_TITLE_CELL_INDEX = 0
private const val TOTAL_SALARY_VALUE_CELL_INDEX = 1

private const val BLANK_ROW_INDEX = 5

private const val TABLE_HEADER_ROW_INDEX = 6
private const val TABLE_BODY_ROW_INDEX = 7


fun totalHeader(sheet: Sheet, headerStyle: CellStyle, items: List<WorkShift>, period: String) {
  val totalSalary = items.sumOf { it.workShiftSalary }
  val totalHours = items.sumOf { it.workedHours }

  sheet.row(TITLE_ROW_INDEX) {
    cell(TITLE_CELL_INDEX, headerStyle) {
      setCellValue("Робочі зміни за період: $period")
    }
  }
  sheet.addMergedRegion(
    CellRangeAddress(TITLE_ROW_INDEX, 1, 0, 5)
  )

  sheet.row(TOTAL_HOURS_ROW_INDEX) {
    cell(TOTAL_HOURS_TITLE_CELL_INDEX, headerStyle) {
      setCellValue("Загальна кількість відпрацьованих годин:")
    }
    cell(TOTAL_HOURS_VALUE_CELL_INDEX, headerStyle) {
      setCellValue(totalHours.toString())
    }
  }

  sheet.row(TOTAL_SALARY_ROW_INDEX) {
    cell(TOTAL_SALARY_TITLE_CELL_INDEX, headerStyle) {
      setCellValue("Виплата за період:")
    }
    cell(TOTAL_SALARY_VALUE_CELL_INDEX, headerStyle) {
      setCellValue(totalSalary.toString())
    }
  }

  sheet.row(BLANK_ROW_INDEX) {
    createCell(TOTAL_HOURS_TITLE_CELL_INDEX)
  }
}

fun headerCellStyle(workbook: XSSFWorkbook): CellStyle {
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

fun tableHeader(sheet: Sheet, cellStyle: CellStyle) = sheet.row(TABLE_HEADER_ROW_INDEX) {
  WorkShiftTableColumns.entries.forEach { column ->
    cell(column.columnIndex, cellStyle) {
      setCellValue(column.header)
    }
  }
}

fun tableBody(sheet: Sheet, items: List<WorkShift>, cellStyle: CellStyle) {
  items.forEachIndexed { index, shift ->
    val row = sheet.createRow(index + TABLE_BODY_ROW_INDEX)
    WorkShiftTableColumns.entries.forEach { column ->
      val cell = row.createCell(column.columnIndex)
      cell.cellStyle = cellStyle
      cell.setCellValue(column.extractor(shift).toString())
    }
  }
}

fun autoSizeCells(sheet: Sheet) {
  for (i in TOTAL_HOURS_TITLE_CELL_INDEX..5) {
    sheet.autoSizeColumn(i)
  }
}

fun Row.cell(
  index: Int,
  cellStyle: CellStyle,
  block: Cell.() -> Unit
) {
  val cell = createCell(index)
  cell.block()
  cell.cellStyle = cellStyle
}

private enum class WorkShiftTableColumns(
  val columnIndex: Int,
  val header: String,
  val extractor: (WorkShift) -> Any
) {
  START_DATE(0, "Дата", { it.startDate }),
  BEGIN_AT(1, "Початок зміни", { it.beginAt }),
  FINISH_AT(2, "Час завершення", { it.finishedAt }),
  BID(3, "Ставка(грн/год)", { it.bid }),
  WORKED_HOURS(4, "Відпрацьовано(год)", { it.workedHours }),
  SALARY(5, "Зароблено за зміну(грн)", { it.workShiftSalary });
}
