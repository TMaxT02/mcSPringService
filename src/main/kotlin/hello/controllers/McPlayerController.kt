package hello.controllers


import hello.classen.McPlayerEntity
import hello.logic.McPlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/players")
class TestController @Autowired constructor(
  private val mcPlayerService: McPlayerService
) {

  @GetMapping("/test/{variable}")
  fun test(@PathVariable variable: String): ResponseEntity<Any> {
    val x = "81a45dc0-57ad-43e9-972b-42d39f852d0f"
    val player = mcPlayerService.getMcPlayerById(x)
    val responseMessage = player?.rang ?: "Player not found"
    return ResponseEntity(responseMessage, HttpStatus.OK)
  }

  @PostMapping
  fun createPlayer(@RequestBody player: McPlayerEntity): ResponseEntity<McPlayerEntity> {
    val createdPlayer = mcPlayerService.saveMcPlayer(player)
    return ResponseEntity(createdPlayer, HttpStatus.CREATED)
  }

  @GetMapping("/{uuid}")
  fun getPlayerById(@PathVariable uuid: String): ResponseEntity<McPlayerEntity> {
    val player = mcPlayerService.getMcPlayerById(uuid)
    return if (player != null) {
      ResponseEntity(player, HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

  @PutMapping("/{uuid}")
  fun updatePlayer(
    @PathVariable uuid: String,
    @RequestBody updatedPlayer: McPlayerEntity
  ): ResponseEntity<McPlayerEntity> {
    val existingPlayer = mcPlayerService.getMcPlayerById(uuid)
    return if (existingPlayer != null) {
      val player = existingPlayer.copy(
        rang = updatedPlayer.rang,
        geld = updatedPlayer.geld,
        homes = updatedPlayer.homes,
        buildRealmAllowedList = updatedPlayer.buildRealmAllowedList
      )
      val savedPlayer = mcPlayerService.saveMcPlayer(player)
      ResponseEntity(savedPlayer, HttpStatus.OK)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }

  @DeleteMapping("/{uuid}")
  fun deletePlayer(@PathVariable uuid: String): ResponseEntity<Void> {
    val existingPlayer = mcPlayerService.getMcPlayerById(uuid)
    return if (existingPlayer != null) {
      mcPlayerService.deleteMcPlayer(uuid)
      ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    } else {
      ResponseEntity(HttpStatus.NOT_FOUND)
    }
  }
}
