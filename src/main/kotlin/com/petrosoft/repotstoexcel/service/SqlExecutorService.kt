package com.petrosoft.repotstoexcel.service

interface SqlExecutorService {
    fun getDataFromSqlScript(fileName: String) : String
}