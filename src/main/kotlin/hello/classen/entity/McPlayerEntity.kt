package hello.classen.entity

import hello.classen.Rang
import hello.classen.dto.*
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
