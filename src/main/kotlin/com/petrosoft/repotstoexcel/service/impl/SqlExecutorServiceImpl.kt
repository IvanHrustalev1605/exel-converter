package com.petrosoft.repotstoexcel.service.impl

import com.petrosoft.repotstoexcel.service.SqlExecutorService
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import kotlin.random.Random


@Service
class SqlExecutorServiceImpl (@Qualifier("JDBC_TEMPLATE_1") private val jdbcTemplate1: JdbcTemplate,
                              @Qualifier("JDBC_TEMPLATE_2") private val jdbcTemplate2: JdbcTemplate): SqlExecutorService {
    override fun getDataFromSqlScript(fileName: String): String {
        var jdbcTemplate: JdbcTemplate
        if (fileName.contains("mtl")) {
            jdbcTemplate = jdbcTemplate1
        } else if (fileName.contains("psd")) {
            jdbcTemplate = jdbcTemplate2
        } else {
            throw Exception("Название файла не подходит!")
        }
        val file = File("./sql-script-files/$fileName.sql")
        val content = file.readLines().joinToString("\n")
        if (content.contains("DELETE") || content.contains("UPDATE") || content.contains("INSERT")) {
            throw Exception("В SQL не должно быть операции обновления или удаления")
        }
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet()
        val data = jdbcTemplate.queryForList(content)
        val head = data[0].keys
        var rowNum = 0
        var rowNumBody = 1
        val rowHead = sheet.createRow(rowNum)

        var cellHeadNum = 0
        var font = workbook.createFont()
        font.bold
        font.fontName = "Arial"
        font.fontHeightInPoints = 14
        var cellStyle = workbook.createCellStyle()
        cellStyle.setFont(font)
        head.forEach{
            val cell = rowHead.createCell(cellHeadNum++)
            cell.cellStyle = cellStyle
            cell.setCellValue(it)
        }

        data.forEach {
            var cellBodyNum = 0
            val rowBody = sheet.createRow(rowNumBody++)
            it.values.forEach { v ->
                val cellBody = rowBody.createCell(cellBodyNum++)
                cellBody.setCellValue(v.toString())
            }
            cellBodyNum = 0
        }
        try {
            val exelFileName = "$fileName-${LocalDateTime.now().dayOfMonth}.${LocalDateTime.now().month.value}.${LocalDateTime.now().year}"
            val out = FileOutputStream(File("./exel-files/$exelFileName.xlsx"))
            workbook.write(out)
            out.close()
            return "Успешно создали файл. Имя файла: $exelFileName"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Произошла ошибка!"
    }
}