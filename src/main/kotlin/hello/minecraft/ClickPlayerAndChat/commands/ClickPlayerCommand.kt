package gg.flyte.template.ClickPlayerAndChat.commands

import gg.flyte.template.ClickPlayerAndChat.gui.ClickPlayerGuiManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ClickPlayerCommand : CommandExecutor {
    private val clickPlayerGuiManager = ClickPlayerGuiManager()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        clickPlayerGuiManager.openVirtualChest(player, sender)
        return true
    }
}
