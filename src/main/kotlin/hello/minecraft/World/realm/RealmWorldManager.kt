package gg.flyte.template.World.realm

import gg.flyte.template.AllLocation.islandTemplate
import gg.flyte.template.PluginTemplate
import gg.flyte.template.PluginTemplate.Companion.instance
import kotlinx.coroutines.*
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.io.*
import java.lang.Runnable
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class RealmWorldManager {
    private object colldowns {
        val cooldowns = mutableMapOf<UUID, Long>()
    }

    fun createWorldForPlayer(player: Player) {
        val playerUUID = player.uniqueId
        val worldName = "world_$playerUUID"
        CoroutineScope(Dispatchers.IO).launch {
            if (hasPlayerWorld(player)) {
                return@launch
            }

            val template = WorldCreator("template").createWorld() ?: return@launch
            copyWorld(template, worldName)
        }
    }

    fun teleportPlayerToHisOwnWorld(player: Player) {
        teleportPlayerToAnotherPlayerWorld(player, player)
    }

    fun teleportPlayerToAnotherPlayerWorld(player: Player, realmOwnerPlayer: Player, withEffects: Boolean = false) {
        val currentTime = System.currentTimeMillis()
        val lastExecutionTime = colldowns.cooldowns[player.uniqueId] ?: 0

        if (currentTime - lastExecutionTime >= TimeUnit.SECONDS.toMillis(3)) {
            colldowns.cooldowns[player.uniqueId] = currentTime

            val targetPlayerUUID = realmOwnerPlayer.uniqueId
            val targetWorldName = "world_$targetPlayerUUID"
            val targetWorld: World? = Bukkit.getWorld(targetWorldName)

            if (targetWorld != null) {
                preloadChunk(targetWorld.spawnLocation)
                setWordborder(targetWorld)

                CoroutineScope(Dispatchers.IO).launch {
                    val spawnLocation = Location(targetWorld, 416.0, 73.5, 201.0)
                    targetWorld.spawnLocation = spawnLocation
                    val highestBlockY = targetWorld.getHighestBlockYAt(spawnLocation)
                    spawnLocation.y = highestBlockY.toDouble() + 1
                    player.sendMessage("test")
                    withContext(PluginTemplate.main){
                        player.sendMessage("test2")
                        if (withEffects) {
                            beamFlyEffect(player)
                            player.world.spawnParticle(Particle.END_ROD, player.location, 20, 0.0, 0.0, 0.0, 0.1)
                        }
                        player.teleport(spawnLocation)
                        if (withEffects) {
                            player.world.spawnParticle(Particle.PORTAL, player.location, 50, 0.5, 0.5, 0.5, 1.0)
                        }
                    }
                }
            } else {
                player.sendMessage("Die Welt des Zielspielers konnte nicht gefunden werden.")
            }
        } else {
            player.sendMessage("Bitte warte noch etwas, bevor du dich wieder teleportierst.")
        }
    }

    fun hasPlayerWorld(player: Player): Boolean {
        val playerUUID = player.uniqueId
        val worldName = "world_$playerUUID"
        return Bukkit.getWorld(worldName) != null
    }

    private fun copyWorld(originalWorld: World, newWorldName: String) {
        val copiedFile = File(Bukkit.getWorldContainer(), newWorldName)
        copyFileStructure(originalWorld.worldFolder, copiedFile)
        WorldCreator(newWorldName).createWorld()
    }

    private fun copyFileStructure(source: File, target: File) {
        try {
            val ignore = listOf("uid.dat", "session.lock")
            if (!ignore.contains(source.name)) {
                if (source.isDirectory) {
                    if (!target.exists()) if (!target.mkdirs()) throw IOException("Couldn't create world directory!")
                    val files = source.list() ?: return
                    for (file in files) {
                        val srcFile = File(source, file)
                        val destFile = File(target, file)
                        copyFileStructure(srcFile, destFile)
                    }
                } else {
                    val `in` = FileInputStream(source)
                    val out = FileOutputStream(target)
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (`in`.read(buffer).also { length = it } > 0) out.write(buffer, 0, length)
                    `in`.close()
                    out.close()
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun preloadChunk(location: Location) {
        val chunk: Chunk = location.chunk
        chunk.load()
    }

    private fun setWordborder(world: World) {
        val centerX = 390.5
        val centerZ = 223.5
        world.worldBorder.center = Location(world, centerX, 0.00, centerZ)
        world.worldBorder.setSize(240.0)
    }

    private fun beamFlyEffect(player: Player) {
        val durationTicks = (3 * 20) // 3 Sekunden in Ticks (1 Sekunde = 20 Ticks)
        val potionEffects = listOf(
            PotionEffect(PotionEffectType.LEVITATION, durationTicks, 0),
            PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 0),
            PotionEffect(PotionEffectType.SLOWNESS, durationTicks, 99)
        )
        player.addPotionEffects(potionEffects)
    }

    fun teleportPlayerToTemplate(player: Player) {
        val worldName = "template"
        val templateWorld: World? = Bukkit.getWorld(worldName)

        if (templateWorld != null) {
            preloadChunk(templateWorld.spawnLocation)
            setWordborder(templateWorld)

            CoroutineScope(Dispatchers.IO).launch {
                val spawnLocation = islandTemplate
                templateWorld.spawnLocation = spawnLocation
                val highestBlockY = templateWorld.getHighestBlockYAt(spawnLocation)
                spawnLocation.y = highestBlockY.toDouble() + 1

                Thread.sleep(2000)
                withContext(Dispatchers.Main) {
                    player.world.spawnParticle(Particle.END_ROD, player.location, 20, 0.0, 0.0, 0.0, 0.1)
                    player.teleport(spawnLocation)
                    player.world.spawnParticle(Particle.PORTAL, player.location, 50, 0.5, 0.5, 0.5, 1.0)
                }
            }
        } else {
            player.sendMessage("Template Welt nicht gefunden")
        }
    }
}
