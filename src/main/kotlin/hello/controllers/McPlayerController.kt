package hello.controller

import hello.classen.dto.McPlayerDTO
import hello.classen.entity.McPlayerEntity
import hello.logic.McPlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/players")
class McPlayerController(
  @Autowired private val mcPlayerService: McPlayerService
) {

  @GetMapping("/{uuid}")
  fun getPlayer(@PathVariable uuid: String): ResponseEntity<McPlayerDTO> {
    val player = mcPlayerService.getMcPlayer(uuid)
    return if (player != null) {
      ResponseEntity.ok(player)
    } else {
      ResponseEntity.notFound().build()
    }
  }

  @PostMapping
  fun createPlayer(@RequestBody dto: McPlayerDTO): ResponseEntity<McPlayerDTO> {
    val playerEntity = McPlayerEntity(
      uuid = dto.uuid,
      rang = dto.rang,
      geld = dto.geld
    )
    val createdPlayer = mcPlayerService.saveMcPlayer(playerEntity)
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer.toDTO())
  }

  @PutMapping("/{uuid}")
  fun updatePlayer(@PathVariable uuid: String, @RequestBody dto: McPlayerDTO): ResponseEntity<McPlayerDTO> {
    val playerEntity = McPlayerEntity(
      uuid = dto.uuid,
      rang = dto.rang,
      geld = dto.geld
    )
    val updatedPlayer = mcPlayerService.updateMcPlayer(uuid, playerEntity)
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
