package hello.minecraft.ClickPlayerAndChat.gui


import hello.minecraft.Money.commands.MoneyManager
import hello.minecraft.Player.PlayerManager
import hello.minecraft.World.realm.RealmWorldManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

class ClickPlayerGuiListener(private val plugin: JavaPlugin) : Listener {
    private val clickPlayerGuiManager = ClickPlayerGuiManager()
    private val realmWorldManager = RealmWorldManager()
    val playerManager = PlayerManager()

    @EventHandler
    fun realmGuiListener(event: InventoryClickEvent) {
        val clickedItem = event.currentItem ?: return
        val player = event.whoClicked as? Player ?: return
        if (event.view.title.contains(clickPlayerGuiManager.inventoryName)) {
            event.isCancelled = true
            val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
            val targetPlayerName = extractPlayerName(event.view.title)
            val targetPlayer = PlayerManager().getVanillaPlayerByDisplayName(targetPlayerName ?: return) ?: return
            val clickedItemName = clickedItem.itemMeta?.displayName ?: return

            when (clickedItemName) {
                clickPlayerGuiManager.realmName ->
                    realmWorldManager.teleportPlayerToAnotherPlayerWorld(player, targetPlayer)

                clickPlayerGuiManager.sendOneCristalname ->
                    MoneyManager().playerPayPlayer(player, targetPlayer, 1)

                clickPlayerGuiManager.allowPlayerToBuildName -> {
                    if (mcPlayer?.playerUUID != targetPlayer.uniqueId.toString()) {
                        player.sendMessage("versuche zu adden")
                        mcPlayer?.buildRealmManager?.addBuildRealmAllowed(player.uniqueId.toString(),targetPlayer.uniqueId.toString())
                        player.sendMessage("versuche zu ICH${player.uniqueId.toString()} TARGET :${targetPlayer.uniqueId.toString()} ")
                        player.sendMessage(mcPlayer?.buildRealmManager?.buildRealmAllowedList?.first()?.otherPlayerUUID.toString())
                    } else {
                        player.sendMessage("Du darfst eh bei dir bauen :)")
                    }
                }
            }
        }
    }

    private fun extractPlayerName(formattedString: String): String? {
        val regex = Regex("§fPlayer: §6(.+)")
        val matchResult = regex.find(formattedString)
        return matchResult?.groups?.get(1)?.value
    }
}