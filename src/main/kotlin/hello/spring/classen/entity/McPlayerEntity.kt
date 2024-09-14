package hello.spring.classen.entity

import hello.spring.classen.Rang
import hello.classen.dto.*
import hello.spring.classen.dto.BuildRealmManager
import hello.spring.classen.dto.HomeManager
import hello.spring.classen.dto.McPlayerDTO
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
  fun toDTO(homeManager: HomeManager? = null, buildRealmManager: BuildRealmManager? = null): McPlayerDTO {
    return McPlayerDTO(
      playerUUID = this.playerUUID,
      rang = this.rang,
      geld = this.geld,
      homeManager = homeManager ?: HomeManager(null),
      buildRealmManager = buildRealmManager ?: BuildRealmManager(null)
    )
  }
}
