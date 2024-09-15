package hello.minecraft.Player.rank


import hello.minecraft.Classes.Rang
import hello.minecraft.Player.PlayerManager
import hello.minecraft.Tablist.TabListManager
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RankCommand : CommandExecutor {
    private val playerManager = PlayerManager()

    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be executed by a player.")
            return true
        }
        if (!sender.isOp) {
            sender.sendMessage("Das darfst du nicht")
            return false
        }
        if (args == null || args.size != 2) {
            sender.sendMessage("Usage: /rang <spieler> <Symbol>")
            return false
        }

        val targetPlayerName = args[0]
        val newSymbolString = args[1]

        val targetPlayer = playerManager.getVanillaPlayerByDisplayName(targetPlayerName)
        if (targetPlayer == null) {
            sender.sendMessage("Der Spieler ist Offline")
            return false
        }

        val newSymbol: Rang? = try {
            Rang.valueOf(newSymbolString)
        } catch (e: IllegalArgumentException) {
            sender.sendMessage("Rang $newSymbolString nicht gefunden.")
            return false
        }

        return updatePlayer(sender, targetPlayer, newSymbol?: return false)
    }

    private fun updatePlayer(
        sender: CommandSender,
        targetPlayer: Player,
        newSymbol: Rang
    ): Boolean {
        val mcPlayer = playerManager.getMcPlayerByUUID(targetPlayer.uniqueId.toString())
        if (mcPlayer != null) {
            mcPlayer.rang = newSymbol
            sender.sendMessage("Player rang updated successfully.")
            TabListManager().updateScoreBoardForAllPlayer()
            return true
        } else {
            sender.sendMessage("Der Spieler wurde nicht gefunden.")
            return false
        }
    }
}
