package gg.flyte.template.World
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class BuildCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be a player to use this command.")
            return true
        }
        //RealmManager().teleportPlayerToBuildWorldOld(sender)
        return true
    }
}
