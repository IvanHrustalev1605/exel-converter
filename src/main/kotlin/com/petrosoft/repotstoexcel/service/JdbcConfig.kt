package com.petrosoft.repotstoexcel.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class JdbcConfig {
    @Bean("DATA_SOURCE_1")
    fun dataSource1() : DataSource {
        val driverManagerDataSource = DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver")
        driverManagerDataSource.username = "postgres"
        driverManagerDataSource.password = "G04occaD"
        driverManagerDataSource.url = "jdbc:postgresql://192.168.0.138:5432/mezhved-test-local"
        return driverManagerDataSource
    }
    @Bean("JDBC_TEMPLATE_1")
    fun jdbcTemplate1() : JdbcTemplate = JdbcTemplate(dataSource1())
    @Bean("DATA_SOURCE_2")
    fun dataSource2() : DataSource {
        val driverManagerDataSource = DriverManagerDataSource()
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver")
        driverManagerDataSource.username = "postgres"
        driverManagerDataSource.password = "G04occaD"
        driverManagerDataSource.url = "jdbc:postgresql://192.168.0.90:19032/public_services_db"
        return driverManagerDataSource
    }
    @Bean("JDBC_TEMPLATE_2")
    fun jdbcTemplate2() : JdbcTemplate = JdbcTemplate(dataSource2())
}