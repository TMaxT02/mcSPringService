package hello.classen.entity

import hello.classen.dto.BuildRealmAllowedDTO
import jakarta.persistence.*

@Entity
@Table(name = "buildrealmallowed")
data class BuildRealmAllowedEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  val buildRealmAllowedID: Int,

  @Column(name = "uuid")
  var playerUUID: String,

  @Column(name = "otherplayeruuid")
  var otherPlayerUUID: String,
) {
  fun toDTO(): BuildRealmAllowedDTO {
    return BuildRealmAllowedDTO(
buildRealmAllowedID = buildRealmAllowedID,
      playerUUID = playerUUID,
      otherPlayerUUID = otherPlayerUUID
    )
  }
}
