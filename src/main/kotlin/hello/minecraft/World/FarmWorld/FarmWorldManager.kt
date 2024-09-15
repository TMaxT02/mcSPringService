package hello.minecraft.World.FarmWorld

import hello.minecraft.PluginTemplate.Companion.instance
import hello.minecraft.World.FarmWorld.FarmWorldManager.objects.cooldowns
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

class FarmWorldManager (){
    private object objects {
        val cooldowns = mutableMapOf<UUID, Long>()
    }

    val async = instance.server.scheduler
    fun rtpToFarmWelt(player: Player) {
        val currentTime = System.currentTimeMillis()
        val lastExecutionTime = cooldowns[player.uniqueId] ?: 0

        // Überprüfe, ob die Zeit seit der letzten Ausführung größer oder gleich 3 Sekunden ist
        if (currentTime - lastExecutionTime >= TimeUnit.SECONDS.toMillis(3)) {
            // Setze die aktuelle Zeit als letzte Ausführungszeit für diesen Spieler
            cooldowns[player.uniqueId] = currentTime

            val runnable = Runnable {
                val farmwelt = Bukkit.getWorld("farmwelt")
                if (farmwelt == null) {
                    player.sendMessage("Fehler: Es gibt keine Farmwelt!")
                }
                val randomLocation = RandomLocationManager().getRandomLocation()
                val chunload = Runnable {
                    preloadChunk(randomLocation)
                }
                async.runTask(instance, chunload)
                val runnableSync = Runnable {
                    player.teleport(randomLocation)
                    player.sendMessage("Du wurdest an einen zufälligen Ort teleportiert.")
                }
                Thread.sleep(2000)
                async.runTask(instance, runnableSync)
            }
            async.runTaskAsynchronously(instance, runnable)
        } else {
            // Wenn die 3-Sekunden-Wartezeit noch nicht abgelaufen ist, informiere den Spieler
        }
    }

    private fun preloadChunk(location: Location) {
        val chunk: Chunk = location.chunk
        chunk.load()
    }
}

