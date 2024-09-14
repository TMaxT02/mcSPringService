package hello.spring.logic

import hello.classen.dto.*
import hello.spring.classen.entity.BuildRealmAllowedEntity
import hello.spring.classen.entity.HomeEntity
import hello.spring.classen.entity.McPlayerEntity
import hello.spring.reposotorys.BuildRealmAllowedRepository
import hello.spring.reposotorys.HomeRepository
import hello.spring.reposotorys.McPlayerRepository
import hello.spring.classen.dto.BuildRealmManager
import hello.spring.classen.dto.HomeManager
import hello.spring.classen.dto.McPlayerDTO
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
      .filter { it.playerUUID == playerId }
      .map { it.toDTO() }

    val buildRealmAllowedList = buildRealmAllowedRepository.findAll()
      .filter { it.playerUUID == playerId }
      .map { it.toDTO() }

    return mcPlayer.toDTO(
      homeManager = HomeManager(homes),
      buildRealmManager = BuildRealmManager(buildRealmAllowedList)
    )
  }

  fun saveMcPlayer(player: McPlayerEntity): McPlayerEntity {
    return mcPlayerRepository.save(player)
  }

  fun updateMcPlayer(playerId: String, updatedPlayerDTO: McPlayerDTO): McPlayerDTO? {
    if (!mcPlayerRepository.existsById(playerId)) {
      return null
    }

    val existingPlayer = mcPlayerRepository.findById(playerId).orElseThrow()
    existingPlayer.rang = updatedPlayerDTO.rang
    existingPlayer.geld = updatedPlayerDTO.geld
    mcPlayerRepository.save(existingPlayer)

    updatedPlayerDTO.homeManager.homes?.forEach { dtoHome ->
      val existingHome = homeRepository.findById(dtoHome.homeid.toLong()).orElse(null)
      if (existingHome != null) {
        existingHome.playerUUID = dtoHome.playerUUID
        existingHome.name = dtoHome.name
        existingHome.locationString = dtoHome.locationString
        homeRepository.save(existingHome)
      } else {
        val newHome = HomeEntity(
          homeid = dtoHome.homeid,
          playerUUID = dtoHome.playerUUID,
          name = dtoHome.name,
          locationString = dtoHome.locationString
        )
        homeRepository.save(newHome)
      }
    }

    updatedPlayerDTO.buildRealmManager.buildRealmAllowedList?.forEach { dtoBuildRealmAllowed ->
      val existingBuildRealmAllowed = buildRealmAllowedRepository.findById(dtoBuildRealmAllowed.buildRealmAllowedID.toLong()).orElse(null)
      if (existingBuildRealmAllowed != null) {
        existingBuildRealmAllowed.playerUUID = dtoBuildRealmAllowed.playerUUID
        existingBuildRealmAllowed.otherPlayerUUID = dtoBuildRealmAllowed.otherPlayerUUID
        buildRealmAllowedRepository.save(existingBuildRealmAllowed)
      } else {
        val newBuildRealmAllowed = BuildRealmAllowedEntity(
          buildRealmAllowedID = dtoBuildRealmAllowed.buildRealmAllowedID,
          playerUUID = dtoBuildRealmAllowed.playerUUID,
          otherPlayerUUID = dtoBuildRealmAllowed.otherPlayerUUID
        )
        buildRealmAllowedRepository.save(newBuildRealmAllowed)
      }
    }

    return existingPlayer.toDTO(
      homeManager = HomeManager(
        homeRepository.findAll().filter { it.playerUUID == playerId }.map { it.toDTO() }
      ),
      buildRealmManager = BuildRealmManager(
        buildRealmAllowedRepository.findAll().filter { it.playerUUID == playerId || it.otherPlayerUUID == playerId }.map { it.toDTO() }
      )
    )
  }

  fun deleteMcPlayer(playerId: String) {
    mcPlayerRepository.deleteById(playerId)
  }
}
