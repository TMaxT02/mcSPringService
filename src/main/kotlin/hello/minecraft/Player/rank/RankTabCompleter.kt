package gg.flyte.template.Player.rank

import gg.flyte.template.Classes.Rang
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class RankTabCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String>? {
        if (args.size == 1) {
            return sender.server.onlinePlayers.map { it.name }
        } else if (args.size == 2) {
            return Rang.entries.map { it.name }
        }
        return null
    }
}
