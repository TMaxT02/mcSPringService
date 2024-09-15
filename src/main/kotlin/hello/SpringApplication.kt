package hello

import hello.logic.McPlayerService
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.sql.DataSource

@SpringBootApplication
class SpringApplication(private val mcPlayerService: McPlayerService, private val dataSource: DataSource) {

    private val log = LoggerFactory.getLogger(SpringApplication::class.java)

    @PostConstruct
    fun init() {
        val uuid = "1234"
        val f = mcPlayerService.getMcPlayer(uuid)

        log.info("${f?.rang}")
        log.info("Created suggestion with ID: ${f?.rang}!!!!")
        checkDatabaseConnection()
    }

    private fun checkDatabaseConnection() {
        try {
            val connection = dataSource.connection
            log.info("Datenbankverbindung erfolgreich!")

            // Zusätzliche Informationen drucken
            val metaData = connection.metaData
            val url = connection.metaData.url
            log.info("Datenbank-URL: $url")
            log.info("Datenbank-Produktname: ${metaData.databaseProductName}")
            log.info("Datenbank-Produktversion: ${metaData.databaseProductVersion}")
            log.info("JDBC-Treibername: ${metaData.driverName}")
            log.info("JDBC-Treiberversion: ${metaData.driverVersion}")
            connection.close()
        } catch (e: Exception) {
            log.error("Fehler bei der Datenbankverbindung: ${e.message}")
        }
    }
}

fun main() {
    runApplication<SpringApplication>()
}
