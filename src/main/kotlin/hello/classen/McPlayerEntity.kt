package hello.classen

import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING


@Entity
@Table(name = "mcplayer")
data class McPlayerEntity(
  @Id
  @Column(name = "uuid")
  val uuid: String,

  @Enumerated(STRING)
  @Column(name = "rang")
  var rang: Rang,

  @Column(name = "geld")
  var geld: Int,

  @OneToMany(mappedBy = "player", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
  var homes: MutableList<HomeEntity> = mutableListOf(),

  @OneToMany(mappedBy = "ownerUUID", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
  var buildRealmAllowedList: MutableList<BuildRealmAllowedEntity> = mutableListOf()
)
enum class Rang() {
  Owner,
  Admin,
  Developer,
  Mod,
  Media,
  Builder,
  Helper,
  Spiele,
  Geld,
  Star,
  Sun,
  Shin,
}
