package hello.minecraft.Classes

import hello.minecraft.Player.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import java.util.*

data class McPlayer(
    val playerUUID: String,
    var rang: Rang,
    var geld: Int,
    val homeManager: McPlayerHome,
    val buildRealmManager: McPlayerBuildRealm
) {
    val playerManager = PlayerManager()

    fun playerAllowedToBuildOnSpawn(): Boolean {
        val uuid = UUID.fromString(playerUUID)
        val player = Bukkit.getPlayer(uuid) ?: return false
        val worldName = player.world.name
        if (player.world.name == "world") {
            return player.isOp && player.gameMode == GameMode.CREATIVE
        }
        if (player.world.name.contains("world_")) {
            val worldPlayerUUID = worldName.substring(6)
            val targetPlayer = playerManager.getMcPlayerByUUID(worldPlayerUUID)
            if (targetPlayer?.buildRealmManager?.isPlayerAllowed(playerUUID) ?: false || worldPlayerUUID==playerUUID )
            {
                return true
            }
        }
       return false
    }
}