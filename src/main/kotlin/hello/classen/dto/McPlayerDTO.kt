package hello.classen.dto

import hello.classen.Rang

data class McPlayerDTO(
  val playerUUID: String,
  val rang: Rang,
  val geld: Int,
  val homes: List<HomeDTO>?,
  val buildRealmAllowedList: List<BuildRealmAllowedDTO>?
)
