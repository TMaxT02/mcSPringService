package hello

import hello.logic.McPlayerService
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application(private val mcPlayerService: McPlayerService) {

	private val log = LoggerFactory.getLogger(Application::class.java)

	@PostConstruct
	fun init() {
    var x = "81a45dc0-57ad-43e9-972b-42d39f852d0f"
		val f = mcPlayerService.getMcPlayer(x)

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
	runApplication<Application>()
}
