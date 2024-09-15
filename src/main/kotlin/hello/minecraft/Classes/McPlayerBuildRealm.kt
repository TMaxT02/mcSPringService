package hello.minecraft.Classes
import hello.minecraft.Classes.Data.BuildRealmAllowedData

class McPlayerBuildRealm(
    val buildRealmAllowedList: MutableList<BuildRealmAllowedData>?
) {
    fun addBuildRealmAllowed(player: String, otherPlayerUUID: String) {
        val buildRealmAllowedData = BuildRealmAllowedData(1,player ,otherPlayerUUID)
        buildRealmAllowedList?.add(buildRealmAllowedData)
    }

    fun removeBuildRealmAllowed(otherPlayerUUID: String) {
        buildRealmAllowedList?.removeIf { it.otherPlayerUUID == otherPlayerUUID }
    }
    fun isPlayerAllowed(otherPlayerUUID: String): Boolean {
        return buildRealmAllowedList?.any { it.otherPlayerUUID == otherPlayerUUID || it.playerUUID == otherPlayerUUID } ?: false
    }
}