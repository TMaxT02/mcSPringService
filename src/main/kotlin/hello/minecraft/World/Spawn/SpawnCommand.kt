package gg.flyte.template.World.Spawn

import gg.flyte.template.AllLocation
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerQuitEvent

class SpawnCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        val player = sender as? Player?: return false
        player.teleport(AllLocation.spawn)
        return true
    }
}
