package gg.flyte.template.World.Spawn

import PlayerManager
import gg.flyte.template.AllLocation
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin

class SpawnListener(private val plugin: JavaPlugin) : Listener {
    private val playerManager = PlayerManager()


    @EventHandler
    fun spawnBreakBlockListener(event: BlockBreakEvent) {
        val player = event.player
        val playerUUIDD = player.uniqueId.toString()
        val mcPlayer = playerManager.getMcPlayerByUUID(playerUUIDD)
        if (mcPlayer == null) {
            event.isCancelled = true
            return
        }
        if (mcPlayer.playerAllowedToBuildOnSpawn()) {
            event.isCancelled = false
        } else {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun spawnPlaceBlockListener(event: BlockPlaceEvent) {
        val player = event.player
        val playerUUIDD = player.uniqueId.toString()
        val mcPlayer = playerManager.getMcPlayerByUUID(playerUUIDD)
        if (mcPlayer == null) {
            event.isCancelled = true
            return
        }
        if (mcPlayer.playerAllowedToBuildOnSpawn()) {
            event.isCancelled = false
        } else {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onInteractAtItemFrame(event: PlayerInteractEntityEvent) {
        val player = event.player
        val playerUUIDD = player.uniqueId.toString()
        val mcPlayer = playerManager.getMcPlayerByUUID(playerUUIDD)
        if (mcPlayer == null) {
            event.isCancelled = true
            return
        }
        if (mcPlayer.playerAllowedToBuildOnSpawn()) {
            event.isCancelled = false
        } else {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onInteractAtItemFrame(event: HangingBreakByEntityEvent) {
        val player = event.remover
        val playerUUIDD = player.uniqueId.toString()
        val mcPlayer = playerManager.getMcPlayerByUUID(playerUUIDD)
        if (mcPlayer == null) {
            event.isCancelled = true
            return
        }
        if (mcPlayer.playerAllowedToBuildOnSpawn()) {
            event.isCancelled = false
        } else {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onFramePlace(event: HangingPlaceEvent) {
        val player = event.player
        val playerUUIDD = player?.uniqueId.toString()
        val mcPlayer = playerManager.getMcPlayerByUUID(playerUUIDD)
        if (mcPlayer == null) {
            event.isCancelled = true
            return
        }
        if (mcPlayer.playerAllowedToBuildOnSpawn()) {
            event.isCancelled = false
        } else {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun tpPlayerTpSpawn(event: PlayerJoinEvent) {
        val player = event.player
        player.teleport(AllLocation.spawn)
        player.gameMode = GameMode.SURVIVAL
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        event.respawnLocation = AllLocation.spawn
    }

    @EventHandler
    fun playStartSound(event: PlayerJoinEvent) {
        val player = event.player
        player.playSound(player.location.clone(), "custom:audio1", 0.12F, 1F)
    }

    @EventHandler
    fun removeJoinMessage(event: PlayerJoinEvent) {
        event.joinMessage = null
    }

    @EventHandler
    fun removeQuitMessage(event: PlayerQuitEvent) {
        event.quitMessage = null
    }
}