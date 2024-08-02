package hello.classen.dto

import hello.classen.entity.HomeEntity

data class HomeDTO(
  val homeid: String,
  val uuid: String,
  val name: String,
  var locationString: String,
  val player: String
) {
  fun toEntity(): HomeEntity {
    return HomeEntity(
      homeid = this.homeid,
      uuid = this.uuid,
      name = this.name,
      locationString = this.locationString,
      player = this.player
    )
  }
}
