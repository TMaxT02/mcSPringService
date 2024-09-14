package hello

import hello.logic.McPlayerService
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringApplication(private val mcPlayerService: McPlayerService) {

	private val log = LoggerFactory.getLogger(SpringApplication::class.java)

	@PostConstruct
	fun init() {
    val uuid = "81a45dc0-57ad-43e9-972b-42d39f852d0f"
		val f = mcPlayerService.getMcPlayer(uuid)

		log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
    log.info("Created suggestion with ID: ${f?.rang}!!!!")
	}
}
fun main() {
	runApplication<SpringApplication>()
}
