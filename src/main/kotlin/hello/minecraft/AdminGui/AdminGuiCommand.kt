import gg.flyte.template.AdminGui.gui.AdminGuiManger
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AdminGuiCommand : CommandExecutor {
    private val adminGuiManger = AdminGuiManger()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be a player to execute this command!")
            return true
        }
        adminGuiManger.open(sender)
        return true
    }
}
