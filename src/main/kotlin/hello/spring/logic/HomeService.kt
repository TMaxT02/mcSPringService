package hello.spring.logic

import hello.spring.classen.dto.HomeDTO
import hello.spring.classen.entity.HomeEntity
import hello.spring.reposotorys.HomeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HomeService(
  private val homeRepository: HomeRepository
) {

  fun createHome(dto: HomeDTO): HomeDTO {
    println("Creating home with DTO: $dto")

    val home = dto.toEntity()
    println("Converted to entity: $home")

    val savedHome = homeRepository.save(home)
    println("Saved home: $savedHome")

    return savedHome.toDTO()
  }
}
