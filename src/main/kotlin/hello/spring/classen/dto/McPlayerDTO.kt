package hello.spring.classen.dto

import hello.spring.classen.Rang

data class McPlayerDTO(
    val playerUUID: String,
    val rang: Rang,
    val geld: Int,
    val homeManager: HomeManager,
    val buildRealmManager: BuildRealmManager
)
