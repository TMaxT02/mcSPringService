package hello.classen.dto

import hello.classen.entity.BuildRealmAllowedEntity

data class BuildRealmAllowedDTO(
  val id: Long,
  val ownerUUID: String,
  val otherPlayerUUID: String
) {
  fun toEntity(): BuildRealmAllowedEntity {
    return BuildRealmAllowedEntity(
      id = this.id,
      ownerUUID = this.ownerUUID,
      otherPlayerUUID = this.otherPlayerUUID
    )
  }
}
