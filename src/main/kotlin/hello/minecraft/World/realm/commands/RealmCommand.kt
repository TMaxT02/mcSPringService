package gg.flyte.template.World.realm

import PlayerManager
import gg.flyte.template.PluginTemplate
import gg.flyte.template.Classes.Data.BuildRealmAllowedData
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class RealmCommand : CommandExecutor {
    private val playerManager = PlayerManager()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("realm", ignoreCase = true)) {
            val player = sender as? Player ?: return true

            if (args.size == 2) {
                val targetPlayerName = args[1]
                val targetOfflinePlayer = Bukkit.getOfflinePlayer(targetPlayerName)

                // Überprüfung, ob der Spieler sich selbst hinzugefügt/entfernt
                if (targetOfflinePlayer.uniqueId == player.uniqueId) {
                    player.sendMessage("Du darfst eh bei dir bauen.")
                    return true
                }

                when {
                    args[0].equals("add", ignoreCase = true) -> {
                        if (targetOfflinePlayer.hasPlayedBefore()) {
                            val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
                            mcPlayer?.buildRealmManager?.addBuildRealmAllowed(player.uniqueId.toString(), targetOfflinePlayer.uniqueId.toString())
                            player.sendMessage("${targetOfflinePlayer.name} wurde zu deinem Build-Realm hinzugefügt.")
                        } else {
                            player.sendMessage("Spieler nicht gefunden oder hat den Server noch nie betreten.")
                        }
                    }
                    args[0].equals("remove", ignoreCase = true) -> {
                        if (targetOfflinePlayer.hasPlayedBefore()) {
                            val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
                            mcPlayer?.buildRealmManager?.removeBuildRealmAllowed(targetOfflinePlayer.uniqueId.toString())
                            player.sendMessage("${targetOfflinePlayer.name} wurde aus deinem Build-Realm entfernt.")
                        } else {
                            player.sendMessage("Spieler nicht gefunden oder hat den Server noch nie betreten.")
                        }
                    }
                    else -> {
                        player.sendMessage("Verwendung: /realm add <Spielername> oder /realm remove <Spielername>")
                    }
                }
            } else {
                player.sendMessage("Verwendung: /realm add <Spielername> oder /realm remove <Spielername>")
            }
        }
        return true
    }
}