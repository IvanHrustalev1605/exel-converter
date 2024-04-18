package com.petrosoft.repotstoexcel.controllers

import com.petrosoft.repotstoexcel.service.SqlExecutorService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/main")
class MainController(private val sqlExecutorService: SqlExecutorService) {
    @Operation(summary = "Сгенерировать exel отчет по sql скрипту", description = "Загружаем sql файл. Указываем правильно имя файла(см. описание Request параметра) " +
            "Получаем сформированный exel по результатам sql запроса",
        parameters = [Parameter(name = "scriptFileName", description = "Имя файла указывается по следующему правилу: в начале указывается префикс, в какой бд ищем " +
                "Т.е mtl - mezhved-test-local, psd - public_services_db. Дальше идет любой разделитель и любое название", required = true, example = "mtl-скрипт")])
    @GetMapping("/generate-exel-file")
    fun generateExelDocument(@RequestParam(required = true) scriptFileName: String) : ResponseEntity<String> {
        return ResponseEntity(sqlExecutorService.getDataFromSqlScript(scriptFileName), HttpStatus.CREATED)
    }
}