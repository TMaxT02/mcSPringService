package hello.classen.entity

import hello.classen.Rang
import hello.classen.dto.BuildRealmAllowedDTO
import hello.classen.dto.HomeDTO
import hello.classen.dto.McPlayerDTO
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING

@Entity
@Table(name = "mcplayer")
data class McPlayerEntity(
  @Id
  @Column(name = "uuid", nullable = false)
  var playerUUID: String,

  @Enumerated(STRING)
  @Column(name = "rang", nullable = false)
  var rang: Rang,

  @Column(name = "geld", nullable = false)
  var geld: Int
) {
  fun toDTO(homes: List<HomeDTO>?=null, buildRealmAllowedList: List<BuildRealmAllowedDTO>?=null): McPlayerDTO {
    return McPlayerDTO(
      playerUUID = this.playerUUID,
      rang = this.rang,
      geld = this.geld,
      homes = homes,
      buildRealmAllowedList = buildRealmAllowedList
    )
  }
}
