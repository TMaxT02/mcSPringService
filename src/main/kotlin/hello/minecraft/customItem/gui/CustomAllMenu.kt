package hello.minecraft.customItem.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin

class CustomAllMenu(private val plugin: JavaPlugin) : Listener {

    private val itemsPerPage = 45
    private val maxItemId = 20768

    fun openGui(player: Player, page: Int) {
        val inventory = plugin.server.createInventory(null, 54, "Page $page")

        val startItemId = (page - 1) * itemsPerPage + 1
        val endItemId = (page * itemsPerPage).coerceAtMost(maxItemId)

        for (id in startItemId ..endItemId) {
            val paper = ItemStack(Material.PAPER)
            val paperMeta: ItemMeta? = paper.itemMeta
            paperMeta?.setCustomModelData(id)
            paperMeta?.setDisplayName("Paper $id")
            paper.itemMeta = paperMeta

            inventory.addItem(paper)
        }

        // Navigation Buttons
        val previousPageButton = ItemStack(Material.ARROW)
        val previousMeta: ItemMeta? = previousPageButton.itemMeta
        previousMeta?.setDisplayName("Previous Page")
        previousPageButton.itemMeta = previousMeta
        inventory.setItem(45, previousPageButton)

        val nextPageButton = ItemStack(Material.ARROW)
        val nextMeta: ItemMeta? = nextPageButton.itemMeta
        nextMeta?.setDisplayName("Next Page")
        nextPageButton.itemMeta = nextMeta
        inventory.setItem(53, nextPageButton)

        player.openInventory(inventory)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.clickedInventory == null || event.currentItem == null || event.whoClicked !is Player) return

        val player = event.whoClicked as Player
        val clickedItem = event.currentItem!!
        val inventoryName = event.view.title

        if (inventoryName.startsWith("Page ")) {
            val page = inventoryName.split(" ")[1].toInt()

            if (clickedItem.type == Material.ARROW) {
                if (clickedItem.itemMeta?.displayName == "Previous Page") {
                    if (page > 1) {
                        openGui(player, page - 1)
                    }
                } else if (clickedItem.itemMeta?.displayName == "Next Page") {
                    if (page * itemsPerPage < maxItemId) {
                        openGui(player, page + 1)
                    }
                }
            }else{
                player.inventory.addItem(clickedItem)
            }

            event.isCancelled = true
        }
    }
}
