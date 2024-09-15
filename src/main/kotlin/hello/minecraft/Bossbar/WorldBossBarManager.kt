package hello.minecraft.Bossbar

import hello.PluginTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import java.lang.Thread.sleep
import java.util.*

class WorldBossBarManager {
    fun b(n: Int = 1): String {
        return "\uF001".repeat(n)
    }

    class Zeit(var stunde: Int, var minute: Int)
    companion object {
        private val bossBars = mutableMapOf<Player, BossBar>()
    }

    fun addWorldBossBarToAllPlayers() {
        for (player in Bukkit.getOnlinePlayers()) {
            addWorldBossBarToPlayer(player)
        }
    }

    fun addWorldBossBarToPlayer(player: Player) {
        val worldName = getWorldName(player)
        val worldTime = getWorldTime(player.world)
        val worldHours = "%02d".format(worldTime.stunde)
        val worldMinute = "%02d".format(worldTime.minute)
        val bg = getBackGroundBar(worldName)
        //  player.sendMessage(worldName.length.toString())
        val back = getBackGroundBack(bg)
        val bossBarText = "$bg${back}字 $worldName     ꈂ${b(22)}书 ${worldHours}:${worldMinute}"
        //  val bossBarText = "ꈂ${b(22)}字12345      體${b(33)}字123456789             甲${b(43)}字abababababab" //13
        val bossBar = Bukkit.createBossBar(bossBarText, BarColor.YELLOW, BarStyle.SOLID)
        bossBar.addPlayer(player)
        bossBars[player] = bossBar
    }

    fun removeBossBarFromPlayer(player: Player) {
        bossBars[player]?.removeAll()
        bossBars.remove(player)
    }

    fun removeAllBossBars() {
        for (bossBar in bossBars.values) {
            bossBar.removeAll()
        }
        bossBars.clear()
    }

    fun getWorldName(player: Player): String {
        var worldName = player.world.name
        if (worldName.startsWith("world_")) {
            val worldPlayerUUID = worldName.substring(6)
            val playeyWorldOwner = Bukkit.getPlayer(UUID.fromString(worldPlayerUUID))
            worldName = "${playeyWorldOwner?.name}'s world"
        }
        if (worldName == "world") {
            worldName = "Spawn"
        }
        return worldName
    }

    private fun getWorldTime(world: World): Zeit {
        val time = world.time
        val hours = ((time / 1000 + 8) % 24).toInt()
        val minutes = ((60 * (time % 1000) / 1000).toInt())
        return Zeit(hours, minutes)
    }

    fun startBossBarThread() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                for (player in Bukkit.getOnlinePlayers()) {
                    withContext(PluginTemplate.main) {
                        syncWorlds()
                        removeAllBossBars()
                        addWorldBossBarToAllPlayers()
                    }
                }
                sleep(100)
            }
        }
    }

    fun getBackGroundBar(worldName: String): String {
        return when {
            worldName.length in 1..5 -> "ꈂ"
            worldName.length in 6..9 -> "體"
            worldName.length in 10..12 -> "甲"
            else -> "甲"  // Falls die Länge außerhalb der Bereiche 1-12 liegt
        }
    }

    fun getBackGroundBack(backGroundBar: String): String {
        // Verwende die when-Klausel, um die entsprechende Anzahl zurückzugeben
        val x = when (backGroundBar) {
            "ꈂ" -> 23
            "體" -> 33
            "甲" -> 43
            else -> 43  // Standardwert, falls das Zeichen nicht gefunden wird
        }
        return b(x)
    }

    private fun syncWorlds() {
        val spawnWorld = Bukkit.getWorld("world") ?: return
        val spawnWorldTime = spawnWorld.time
        val spawnWorldWeather = spawnWorld.weatherDuration
        val spawnWorldStorm = spawnWorld.hasStorm()
        val spawnWorldThunder = spawnWorld.isThundering

        Bukkit.getWorlds().filter { it != spawnWorld }.forEach { world ->
            world.time = spawnWorldTime
            world.setStorm(spawnWorldStorm)
            world.isThundering = spawnWorldThunder
            world.weatherDuration = spawnWorldWeather
        }
    }
}