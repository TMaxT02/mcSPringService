package gg.flyte.template

import gg.flyte.template.repo.McPlayerService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext

@SpringBootApplication
class TestApplication

fun startSpringBoot(args: Array<String>) {
    val context: ApplicationContext = runApplication<TestApplication>(*args)

    // Hole den McPlayerService aus dem ApplicationContext
    val mcPlayerService = context.getBean(McPlayerService::class.java)

    // Beispiel UUID, um einen McPlayer zu finden
    val uuid = "81a45dc0-57ad-43e9-972b-42d39f852d0f"

    // Rufe den McPlayer ab und gebe ihn aus
    val player = mcPlayerService.getMcPlayerById(uuid)
    for (x in 1..10)
    if (player != null) {
        println("Retrieved player: ${player.rang}")
    } else {
        println("Player with UUID $uuid not found.")
    }
}
fun main(){
    startSpringBoot(emptyArray())
}
