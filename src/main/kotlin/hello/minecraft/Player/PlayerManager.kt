package hello.minecraft.Player
import hello.minecraft.Classes.McPlayer
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
        val cachedPlayer = get(uuid)
        if (cachedPlayer != null) {
            return cachedPlayer
        } else {

            val mcPlayer = runBlocking { McPlayerApiClient.getPlayerById(uuid) }
            if (mcPlayer != null) {
                add(mcPlayer)
            }
            return mcPlayer
        }
    }

    fun updateMcPlayer(updatedPlayer: McPlayer): McPlayer? {
        val updatedMcPlayer = runBlocking { McPlayerApiClient.updatePlayer(updatedPlayer.playerUUID, updatedPlayer) }
        if (updatedMcPlayer != null) {
            remove(updatedPlayer.playerUUID)
            add(updatedMcPlayer)
        }
        return updatedMcPlayer
    }
}