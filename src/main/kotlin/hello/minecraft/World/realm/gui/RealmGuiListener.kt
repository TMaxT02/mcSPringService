package gg.flyte.template.World.realm.gui

import PlayerManager
import gg.flyte.template.PluginTemplate
import gg.flyte.template.World.realm.RealmWorldManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.time.measureTime

class RealmGuiListener : Listener {
    private val guiManager = PluginTemplate.realmGuiManager
    private val playerManager = PlayerManager()
    private val realmWorldManager = RealmWorldManager()

    private val previousPageName = "§7Vorherige Seite"
    private val nextPageName = "§7Nächste Seite"

    @EventHandler
    fun realmGuiListener(event: InventoryClickEvent) {
        val clickedItem = event.currentItem ?: return
        val player = event.whoClicked as Player
        val itemName = clickedItem.itemMeta?.displayName ?: return

        if (event.view.title.contains("§3§lRealms")) {
            event.isCancelled = true
            player.sendMessage(event.slot.toString())
            when (itemName) {
                CustomItems.BACK_BUTTON.itemMeta.displayName -> guiManager.openPreviousPage(player) // Vorherige Seite
                CustomItems.NEXT_BUTTON.itemMeta.displayName -> guiManager.openNextPage(player) // Nächste Seite
            }

            if (clickedItem.type == Material.PLAYER_HEAD) {
                val realmPlayerName = itemName.trim()
                val realmPlayer: Player = playerManager.getVanillaPlayerByDisplayName(realmPlayerName) ?: return
                realmWorldManager.teleportPlayerToAnotherPlayerWorld(player, realmPlayer)
                event.inventory.close()
            }

            if (clickedItem.type == Material.IRON_INGOT && clickedItem.itemMeta?.customModelData == 1) {
                realmWorldManager.teleportPlayerToHisOwnWorld(player)
                event.inventory.close()
            }
        }
    }

}