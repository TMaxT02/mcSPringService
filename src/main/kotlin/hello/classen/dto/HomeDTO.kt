package hello.classen.dto

import hello.classen.entity.HomeEntity

data class HomeDTO(
  val homeid: Int,
  val playerUUID: String,
  val name: String,
  var locationString: String,
) {
  fun toEntity(): HomeEntity {
    return HomeEntity(
      homeid = this.homeid,
      playerUUID = this.playerUUID,
      name = this.name,
      locationString = this.locationString,
    )
  }
}
