package hello.minecraft.AdminGui.gui

import hello.minecraft.customItem.CustomItems
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class AdminGuiManger() {
    val inventoryName = "§c§lAdmin-Menue"
    val reports = "Reports"
    val adminArea = "§3Admin-Area"
    val customItems = "Custom items"
    val bauwelt = "§2Bauwelt"
    val islandTemplate = "§eIsland-Tempalte"
    fun b(n: Int = 1): String {
        return "\uF001".repeat(n)
    }

    fun open(player: Player) {
        val inventory: Inventory = Bukkit.createInventory(null, 27, inventoryName)

        val meldungen = ItemStack(Material.PLAYER_HEAD)
        val meldungenMeta: ItemMeta = meldungen.itemMeta ?: return
        meldungenMeta.setDisplayName(reports)
        meldungen.itemMeta = meldungenMeta

        val adminAreaItem = ItemStack(Material.DIAMOND_BLOCK)
        val adminAreaItemMeta: ItemMeta = adminAreaItem.itemMeta ?: return
        adminAreaItemMeta.setDisplayName(adminArea)
        adminAreaItem.itemMeta = adminAreaItemMeta

        val customItemsItem = ItemStack(Material.ENCHANTED_GOLDEN_APPLE)
        val custumItemsItemMeta: ItemMeta? = customItemsItem.itemMeta
        custumItemsItemMeta?.setDisplayName(customItems)
        customItemsItem.itemMeta = custumItemsItemMeta

        val story = ItemStack(Material.OAK_SAPLING)
        val storyMeta: ItemMeta? = story.itemMeta
        storyMeta?.setDisplayName(bauwelt)
        story.itemMeta = storyMeta

        val islandTemplateItem = ItemStack(Material.GRASS_BLOCK)
        val islandTemplateItemMeta: ItemMeta? = islandTemplateItem.itemMeta
        islandTemplateItemMeta?.setDisplayName(islandTemplate)
        islandTemplateItem.itemMeta = islandTemplateItemMeta

        inventory.setItem(0, meldungen)
        inventory.setItem(8, adminAreaItem)
        inventory.setItem(9, customItemsItem)
        inventory.setItem(17, story)
        inventory.setItem(26, islandTemplateItem)

        player.openInventory(inventory)
    }

    val startModelData = 5004
    val endModelData = 5433
    val exceptions = setOf(
        5410, 5418, 5133, 5135, 5137, 5139, 5141, 5143, 5170,
        5183, 5186, 5256, 5257, 5264, 5265, 5272, 5273, 5274,
        5275, 5276, 5277, 5278, 5279, 5342, 5354, 5354, 5397,
        5398, 5399, 5389, 5364, 5370, 5371, 5396, 5285
    )

    fun createAndOpenPaperGUI(player: Player, page: Int) {
        val papersPerPage = 45
        val totalPapers = (endModelData - startModelData + 1) - exceptions.size
        val totalPages = (totalPapers + papersPerPage - 1) / papersPerPage

        val inventoryTitle = "Custom GUI Page: ${page + 1}"
        val inventory = Bukkit.createInventory(null, 54, inventoryTitle)

        val startIndex = page * papersPerPage
        var modelData = startModelData
        var papersAdded = 0

        // Skip to the starting point for the current page
        while (papersAdded < startIndex) {
            if (modelData !in exceptions) {
                papersAdded++
            }
            modelData++
            // If we reach endModelData, break the loop
            if (modelData > endModelData) break
        }

        // Add items to the inventory for the current page
        var currentPapersOnPage = 0
        while (currentPapersOnPage < papersPerPage && modelData <= endModelData) {
            if (modelData !in exceptions) {
                val itemStack = ItemStack(Material.PAPER)
                val meta: ItemMeta = itemStack.itemMeta ?: continue
                meta.setCustomModelData(modelData)
                meta.setDisplayName("${ChatColor.GREEN}Paper $modelData")
                itemStack.itemMeta = meta

                inventory.setItem(currentPapersOnPage, itemStack)
                currentPapersOnPage++
            }
            modelData++
        }

        // Add navigation arrows
        if (page > 0) {
            inventory.setItem(45, CustomItems.BACK_BUTTON)
        } else {
            inventory.setItem(45, CustomItems.BACK_BUTTON_DIM)
        }

        if (currentPapersOnPage == papersPerPage && modelData <= endModelData) {
            inventory.setItem(53, CustomItems.NEXT_BUTTON)
        }else{
            inventory.setItem(53, CustomItems.NEXT_BUTTON_DIM)
        }

        player.openInventory(inventory)
    }
}