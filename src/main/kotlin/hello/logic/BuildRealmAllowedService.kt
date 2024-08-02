package hello.logic

import hello.classen.dto.BuildRealmAllowedDTO
import hello.classen.entity.BuildRealmAllowedEntity
import hello.reposotorys.BuildRealmAllowedRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BuildRealmAllowedService(
  @Autowired private val buildRealmAllowedRepository: BuildRealmAllowedRepository
) {

  fun createBuildRealmAllowed(dto: BuildRealmAllowedDTO): BuildRealmAllowedDTO {
    val buildRealmAllowed = dto.toEntity()
    buildRealmAllowedRepository.save(buildRealmAllowed)
    return buildRealmAllowed.toDTO()
  }

  fun getBuildRealmAllowed(id: Long): BuildRealmAllowedDTO? {
    return buildRealmAllowedRepository.findById(id).orElse(null)?.toDTO()
  }

  fun updateBuildRealmAllowed(id: Long, dto: BuildRealmAllowedDTO): BuildRealmAllowedDTO? {
    val buildRealmAllowed = buildRealmAllowedRepository.findById(id).orElse(null) ?: return null
    buildRealmAllowed.ownerUUID = dto.ownerUUID
    buildRealmAllowed.otherPlayerUUID = dto.otherPlayerUUID
    buildRealmAllowedRepository.save(buildRealmAllowed)
    return buildRealmAllowed.toDTO()
  }

  fun deleteBuildRealmAllowed(id: Long) {
    buildRealmAllowedRepository.findById(id).ifPresent { buildRealmAllowedRepository.delete(it) }
  }
}
