package hello.minecraft.Home.gui

import hello.minecraft.Player.PlayerManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

class HomeGuiListener(private val plugin: JavaPlugin) : Listener {
    private val homeGuiManager = HomeGuiManager()
    private val playerManager = PlayerManager()

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val clickedItem = event.currentItem ?: return
        val player = event.whoClicked as? Player ?: return
        val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
        if (event.view.title == homeGuiManager.inventoryName) {
            val clickedItemName = clickedItem.itemMeta.displayName
            event.isCancelled = true

            //select home
            if (isDye(clickedItem.type)) {
                val cursorPosition = event.rawSlot + 9
                homeGuiManager.openHomeMenu(player, cursorPosition)
            }

            when (clickedItemName) {
                homeGuiManager.createNewHomeName -> {
                    if (clickedItem.type == Material.DIAMOND_BLOCK) {
                        homeGuiManager.createHome(player)
                    }
                }

                homeGuiManager.deleteHomeName -> {
                    val inventory = event.inventory
                    val homeToDelete = homeGuiManager.getHomeAboutCursor(inventory, player) ?: return
                    homeGuiManager.deleteHome(player, homeToDelete.name)
                    mcPlayer?.homeManager?.removeHome(homeToDelete)
                    homeGuiManager.openHomeMenu(player)
                }

                homeGuiManager.teleportHomeName -> {
                    player.sendMessage(clickedItemName)
                    val homeToTeleport = homeGuiManager.getHomeAboutCursor(event.inventory, player) ?: return
                    mcPlayer?.homeManager?.teleportToHome(homeToTeleport.name, player)
                }
            }
            return
        }
    }

    private fun isDye(material: Material): Boolean {
        val dyeMaterials = listOf(
            Material.BLACK_DYE,
            Material.BLUE_DYE,
            Material.BROWN_DYE,
            Material.CYAN_DYE,
            Material.GRAY_DYE,
            Material.GREEN_DYE,
            Material.LIGHT_BLUE_DYE,
            Material.LIGHT_GRAY_DYE,
            Material.LIME_DYE,
            Material.MAGENTA_DYE,
            Material.ORANGE_DYE,
            Material.PINK_DYE,
            Material.PURPLE_DYE,
            Material.RED_DYE,
            Material.WHITE_DYE,
            Material.YELLOW_DYE
        )
        return material in dyeMaterials
    }
}