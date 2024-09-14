package hello.spring.classen.entity

import hello.spring.classen.dto.HomeDTO
import jakarta.persistence.*

@Entity
@Table(name = "homes")
data class HomeEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "homeid", nullable = false)
  val homeid: Int,

  @Column(name = "uuid", nullable = false)
  var playerUUID: String,

  @Column(name = "name", nullable = false)
  var name: String,

  @Column(name = "location", nullable = false)
  var locationString: String,
) {
  fun toDTO(): HomeDTO {
    return HomeDTO(homeid, playerUUID, name, locationString)
  }
}
