package hello.classen.dto

import hello.classen.entity.BuildRealmAllowedEntity

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
