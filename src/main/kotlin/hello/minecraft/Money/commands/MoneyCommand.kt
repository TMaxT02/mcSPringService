package hello.minecraft.Money.commands
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MoneyCommand : CommandExecutor {
    private val moneyManager = MoneyManager()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("money", ignoreCase = true)) {
            var player = sender as? Player ?: return true
            moneyManager.moneyAmount(player)
        }
        return true
    }
}
