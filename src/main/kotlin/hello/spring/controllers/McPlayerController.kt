package hello.controller

import hello.spring.classen.dto.McPlayerDTO
import hello.spring.classen.entity.McPlayerEntity
import hello.spring.logic.BuildRealmAllowedService
import hello.spring.logic.HomeService
import hello.spring.logic.McPlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/players")
class McPlayerController(
  @Autowired private val mcPlayerService: McPlayerService,
  @Autowired private val homeService: HomeService,
  @Autowired private val buildRealmAllowedService: BuildRealmAllowedService
) {

  @GetMapping("/{uuid}")
  fun getPlayer(@PathVariable uuid: String): ResponseEntity<McPlayerDTO> {
    val playerDTO = mcPlayerService.getMcPlayer(uuid)
    return if (playerDTO != null) {
      ResponseEntity.ok(playerDTO)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PostMapping
  fun createPlayer(@RequestBody dto: McPlayerDTO): ResponseEntity<McPlayerDTO> {
    val playerEntity = McPlayerEntity(
      playerUUID = dto.playerUUID,
      rang = dto.rang,
      geld = dto.geld
    )
    val createdPlayer = mcPlayerService.saveMcPlayer(playerEntity)
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer.toDTO())
  }

  @PutMapping("/{playerId}")
  fun updatePlayer(@PathVariable playerId: String, @RequestBody updatedPlayerDTO: McPlayerDTO): ResponseEntity<McPlayerDTO> {
    val updatedPlayer = mcPlayerService.updateMcPlayer(playerId, updatedPlayerDTO)
    return if (updatedPlayer != null) {
      ResponseEntity.ok(updatedPlayer)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping("/{uuid}")
  fun deletePlayer(@PathVariable uuid: String): ResponseEntity<Void> {
    mcPlayerService.deleteMcPlayer(uuid)
    return ResponseEntity.noContent().build()
  }
}
