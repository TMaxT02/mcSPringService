package hello.minecraft.World.realm.commands

import hello.minecraft.PluginTemplate
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RealmsCommand : CommandExecutor {
    val  realmGuiManager=  PluginTemplate.realmGuiManager
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be a player to use this command.")
            return true
        }
        realmGuiManager.openRealmChest(sender, 0)
        return true
    }
}
