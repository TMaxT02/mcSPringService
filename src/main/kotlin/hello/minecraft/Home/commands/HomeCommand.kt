package hello.minecraft.Home.commands

import hello.minecraft.Home.gui.HomeGuiManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class HomeCommand(private val plugin: JavaPlugin) : CommandExecutor {
private val homeGuiManagerX = HomeGuiManager()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be a player to execute this command!")
            return true
        }

        homeGuiManagerX.openHomeMenu(sender)
        return true
    }
}
