package gg.flyte.template.World.realm.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class RealmCommandTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (command.name.equals("realm", ignoreCase = true)) {
            if (args.size == 1) {
                return listOf("add", "remove").filter { it.startsWith(args[0], ignoreCase = true) }
            } else if (args.size == 2 && (args[0].equals("add", ignoreCase = true) || args[0].equals("remove", ignoreCase = true))) {
                return Bukkit.getOnlinePlayers().map { it.name }.filter { it.startsWith(args[1], ignoreCase = true) }
            }
        }
        return emptyList()
    }
}
