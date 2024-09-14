package gg.flyte.template.AdminGui.gui

import gg.flyte.template.AllLocation
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin

class AdminGuiListener(private val plugin: JavaPlugin) : Listener {
    val adminGuiManger = AdminGuiManger()

    @EventHandler
    fun realmGuiListener(event: InventoryClickEvent) {


        if (event.view.title == adminGuiManger.inventoryName) {
            val clickedItemName = event.currentItem?.itemMeta?.displayName
            val player = event.whoClicked as Player
            event.isCancelled = true

            when (clickedItemName) {
                adminGuiManger.bauwelt -> player.teleport(AllLocation.story)
                adminGuiManger.adminArea -> player.teleport(AllLocation.adminArea)
                adminGuiManger.islandTemplate -> player.teleport(AllLocation.islandTemplate)
                adminGuiManger.customItems -> adminGuiManger.createAndOpenPaperGUI(player, 0)
            }
            event.inventory.close()
        }
    }

    @EventHandler
    fun handleInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val clickedItem = event.currentItem ?: return
        val inventoryView = event.view

        val inventoryTitle = inventoryView.title
        if (!inventoryTitle.startsWith("Custom GUI Page:")) return

        event.isCancelled = true

        val displayName = clickedItem.itemMeta?.displayName ?: return
        val currentPage = getCurrentPage(inventoryTitle)

        when (displayName) {
            CustomItems.BACK_BUTTON.itemMeta.displayName -> {
                if (currentPage > 1) {
                    adminGuiManger.createAndOpenPaperGUI(player, currentPage - 2)
                }
            }

            CustomItems.NEXT_BUTTON.itemMeta.displayName -> {
                val totalPages =
                    (adminGuiManger.endModelData - adminGuiManger.startModelData + 1 - adminGuiManger.exceptions.size + 45 - 1) / 45
                if (currentPage < totalPages) {
                    adminGuiManger.createAndOpenPaperGUI(player, currentPage)
                }
            }
        }
    }

    private fun getCurrentPage(inventoryTitle: String): Int {
        return inventoryTitle.substringAfter(": ").toIntOrNull() ?: 1
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked
        val clickedItem = event.currentItem ?: return
        val inventoryName = event.view.title
        if (inventoryName.contains("Custom GUI")) {
            if (event.slot !in(45..53)) {
                player.inventory.addItem(clickedItem)
                event.isCancelled = true
            }
        }
    }

}