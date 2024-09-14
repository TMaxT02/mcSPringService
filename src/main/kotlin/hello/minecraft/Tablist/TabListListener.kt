package gg.flyte.template.Tablist

import TabListManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class TabListListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        TabListManager().updateScoreBoardForAllPlayer()
    }
    @EventHandler
    fun onPlayerJoin(event: PlayerQuitEvent) {
        TabListManager().updateScoreBoardForAllPlayer()
    }
}
