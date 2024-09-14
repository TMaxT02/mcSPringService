package gg.flyte.template.Money.commands

import PlayerManager
import ScoreboardManager
import org.bukkit.entity.Player

class MoneyManager() {
    private val playerManager = PlayerManager()

    fun playerPayPlayer(sender: Player, target: Player, amount: Int) {
        val mcPlayer = playerManager.getMcPlayerByUUID(sender.uniqueId.toString())
        val mcPlayerTarget = playerManager.getMcPlayerByUUID(target.uniqueId.toString())
        if (mcPlayer?.geld!! < amount) {
            sender.sendMessage("Du hast nicht genug Geld.")
            return
        }
        if (mcPlayer.playerUUID == mcPlayerTarget?.playerUUID) {
            sender.sendMessage("Du darfst dir selber kein Geld schicken!.")
            return
        }
        mcPlayer.geld -= amount
        mcPlayerTarget!!.geld += amount
        sender.sendMessage("Du hast $amount Geld an ${target.displayName} gesendet.")
        target.sendMessage("Du hast $amount Geld von ${sender.displayName} erhalten.")
        ScoreboardManager().updateScoreboard(sender)
        ScoreboardManager().updateScoreboard(playerManager.getVanillaPlayerByDisplayName(target.name)!!)
    }

    fun moneyAmount(player: Player) {
        val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
        val money = mcPlayer?.geld
        player.sendMessage("Du hast $money Geld.")
    }
}
