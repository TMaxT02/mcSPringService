package hello.minecraft.World.Spawn

import hello.minecraft.AllLocation
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

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
