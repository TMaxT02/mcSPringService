package hello.logic

import hello.classen.dto.HomeDTO
import hello.classen.entity.HomeEntity
import hello.reposotorys.HomeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HomeService(
  @Autowired private val homeRepository: HomeRepository
) {

  fun createHome(dto: HomeDTO): HomeDTO {
    val home = dto.toEntity()
    homeRepository.save(home)
    return home.toDTO()
  }

  fun getHome(homeid: String): HomeDTO? {
    return homeRepository.findById(homeid).orElse(null)?.toDTO()
  }

  fun updateHome(homeid: String, dto: HomeDTO): HomeDTO? {
    val home = homeRepository.findById(homeid).orElse(null) ?: return null
    home.name = dto.name
    home.locationString = dto.locationString
    homeRepository.save(home)
    return home.toDTO()
  }

  fun deleteHome(homeid: String) {
    homeRepository.findById(homeid).ifPresent { homeRepository.delete(it) }
  }
}
