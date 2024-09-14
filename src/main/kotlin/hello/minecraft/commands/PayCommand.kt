package gg.flyte.template.Money.commands// gg.flyte.template.money.PayCommand.kt

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PayCommand : CommandExecutor {
    private val moneyManager = MoneyManager()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("pay", ignoreCase = true)) {
            val player = sender as? Player ?: return true
                if (args.size == 2) {
                    val target = args[0]
                    val amountStr = args[1]
                    val targetPlayer: Player = sender.server.getPlayer(target)!!
                    moneyManager.playerPayPlayer(player, targetPlayer,amountStr.toInt())
                } else {
                    sender.sendMessage("Verwendung: /pay <Spielername> <Betrag>")
                }

            return true
        }
        return false
    }
}