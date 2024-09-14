import gg.flyte.template.Player.McPlayerApiClient
import gg.flyte.template.Classes.McPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import kotlinx.coroutines.runBlocking

class PlayerManager {

    private companion object PlayerCache {
        private val cache = mutableListOf<McPlayer>()

        fun get(uuid: String): McPlayer? = cache.find { it.playerUUID == uuid }

        fun add(mcPlayer: McPlayer) {
            cache.removeIf { it.playerUUID == mcPlayer.playerUUID }
            cache.add(mcPlayer)
        }

        fun remove(uuid: String) {
            cache.removeAll { it.playerUUID == uuid }
        }
    }
    fun getVanillaPlayerByDisplayName(displayName: String): Player? {
        return Bukkit.getPlayer(displayName)
    }


    fun getMcPlayerByUUID(uuid: String): McPlayer? {
        val cachedPlayer = PlayerCache.get(uuid)
        if (cachedPlayer != null) {
            return cachedPlayer
        } else {

            val mcPlayer = runBlocking { McPlayerApiClient.getPlayerById(uuid) }
            if (mcPlayer != null) {
                PlayerCache.add(mcPlayer)
            }
            return mcPlayer
        }
    }

    fun updateMcPlayer(updatedPlayer: McPlayer): McPlayer? {
        val updatedMcPlayer = runBlocking { McPlayerApiClient.updatePlayer(updatedPlayer.playerUUID, updatedPlayer) }
        if (updatedMcPlayer != null) {
            PlayerCache.remove(updatedPlayer.playerUUID)
            PlayerCache.add(updatedMcPlayer)
        }
        return updatedMcPlayer
    }
}