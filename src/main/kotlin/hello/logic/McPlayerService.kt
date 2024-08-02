package hello.logic

import hello.classen.dto.McPlayerDTO
import hello.classen.entity.McPlayerEntity
import hello.reposotorys.BuildRealmAllowedRepository
import hello.reposotorys.HomeRepository
import hello.reposotorys.McPlayerRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class McPlayerService(
  private val mcPlayerRepository: McPlayerRepository,
  private val homeRepository: HomeRepository,
  private val buildRealmAllowedRepository: BuildRealmAllowedRepository
) {

  fun getMcPlayer(playerId: String): McPlayerDTO? {
    val mcPlayer = mcPlayerRepository.findById(playerId).orElse(null) ?: return null

    val homes = homeRepository.findAll()
      .filter { it.player == playerId }
      .map { it.toDTO() }

    val buildRealmAllowedList = buildRealmAllowedRepository.findAll()
      .filter { it.ownerUUID == playerId || it.otherPlayerUUID == playerId }
      .map { it.toDTO() }

    return mcPlayer.toDTO(homes, buildRealmAllowedList)
  }

  fun saveMcPlayer(player: McPlayerEntity): McPlayerEntity {
    return mcPlayerRepository.save(player)
  }

  fun updateMcPlayer(playerId: String, updatedPlayer: McPlayerEntity): McPlayerDTO? {
    return if (mcPlayerRepository.existsById(playerId)) {
      updatedPlayer.uuid = playerId
      mcPlayerRepository.save(updatedPlayer).toDTO(
        homeRepository.findAll().filter { it.player == playerId }.map { it.toDTO() },
        buildRealmAllowedRepository.findAll().filter { it.ownerUUID == playerId || it.otherPlayerUUID == playerId }.map { it.toDTO() }
      )
    } else {
      null
    }
  }

  fun deleteMcPlayer(playerId: String) {
    mcPlayerRepository.deleteById(playerId)
  }
}
