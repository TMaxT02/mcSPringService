package hello.controllers


import hello.classen.McPlayerEntity
import hello.logic.McPlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController @Autowired constructor(
  private val mcPlayerService: McPlayerService
) {

  @GetMapping("/test/{variable}")
  fun test(@PathVariable variable: String): ResponseEntity<String> {
    // Verwende den McPlayerService hier, wenn nötig
    // Zum Beispiel:
    val x  = "81a45dc0-57ad-43e9-972b-42d39f852d0f"
    val player = mcPlayerService.getMcPlayerById(x)

    // Erstelle die Antwort, die die Path-Variable enthält
    val responseMessage = "${player?.rang}"

    // Gebe die Antwort zurück
    return ResponseEntity(responseMessage, HttpStatus.OK)
  }
}
