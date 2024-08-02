package hello.classen.entity

import hello.classen.dto.HomeDTO
import jakarta.persistence.*

@Entity
@Table(name = "homes")
data class HomeEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "homeid", nullable = false)
  val homeid: String,

  @Column(name = "uuid", nullable = false)
  val uuid: String,

  @Column(name = "name", nullable = false)
  var name: String,

  @Column(name = "location", nullable = false)
  var locationString: String,

  @JoinColumn(name = "uuid", nullable = false)
  val player: String
) {
  fun toDTO(): HomeDTO {
    return HomeDTO(homeid, uuid, name, locationString, player)
  }
}
