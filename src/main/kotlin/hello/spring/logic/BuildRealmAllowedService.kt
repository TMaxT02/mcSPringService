package hello.spring.logic

import hello.spring.classen.dto.BuildRealmAllowedDTO
import hello.spring.classen.dto.HomeDTO
import hello.spring.classen.entity.BuildRealmAllowedEntity
import hello.spring.reposotorys.BuildRealmAllowedRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BuildRealmAllowedService(
  @Autowired private val buildRealmAllowedRepository: BuildRealmAllowedRepository
) {


  fun createBuildRealmAllowed(dto: BuildRealmAllowedDTO): BuildRealmAllowedDTO {
    println("Creating home with DTO: $dto")

    val home = dto.toEntity()
    println("Converted to entity: $home")

    val buildRelam = buildRealmAllowedRepository.save(home)

    return buildRelam.toDTO()
  }

  fun getBuildRealmAllowed(id: Long): BuildRealmAllowedDTO? {
    return buildRealmAllowedRepository.findById(id).orElse(null)?.toDTO()
  }

  fun updateBuildRealmAllowed(id: Long, dto: BuildRealmAllowedDTO): BuildRealmAllowedDTO? {
    val buildRealmAllowed = buildRealmAllowedRepository.findById(id).orElse(null) ?: return null
    buildRealmAllowed.playerUUID = dto.playerUUID
   //buildRealmAllowed.otherPlayerUUID = dto.otherPlayerUUID
    buildRealmAllowedRepository.save(buildRealmAllowed)
    return buildRealmAllowed.toDTO()
  }

  fun deleteBuildRealmAllowed(id: Long) {
    buildRealmAllowedRepository.findById(id).ifPresent { buildRealmAllowedRepository.delete(it) }
  }
}
