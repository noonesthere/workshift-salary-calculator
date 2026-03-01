package calculator.workshift

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream

class WorkShiftDocumentExporter {

  fun export(period: String, items: List<WorkShift>): ByteArray {

    return XSSFWorkbook().use { workbook ->
      val sheet = workbook.createSheet("Робочі зміни")

      val headerStyle = headerCellStyle(workbook)
      val baseCellStyle = baseCellStyle(workbook)

      totalHeader(sheet, headerStyle, items, period)
      tableHeader(sheet, headerStyle)
      tableBody(sheet, items, baseCellStyle)
      autoSizeCells(sheet)

      ByteArrayOutputStream().use { output ->
        workbook.write(output)
        output.toByteArray()
      }
    }
  }
}
