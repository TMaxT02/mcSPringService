package hello.spring.classen.dto

import hello.spring.classen.entity.BuildRealmAllowedEntity

data class BuildRealmAllowedDTO(
  val buildRealmAllowedID: Int,
  val playerUUID: String,
  val otherPlayerUUID: String
) {
  fun toEntity(): BuildRealmAllowedEntity {
    return BuildRealmAllowedEntity(
      buildRealmAllowedID = this.buildRealmAllowedID,
      playerUUID = this.playerUUID,
    otherPlayerUUID = this.otherPlayerUUID
    )
  }
}
