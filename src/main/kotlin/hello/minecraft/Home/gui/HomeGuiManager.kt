package hello.minecraft.Home.gui


import hello.minecraft.Classes.Data.HomeData
import hello.minecraft.Player.PlayerManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class HomeGuiManager {
    val inventoryName = "§3§lHomes"

    // Item-Namen als Variablen
     val createNewHomeName = "§bCreate New Home"
     val infoItemName = "§fInfo"
     val teleportHomeName = "§bTeleport Home"
     val deleteHomeName = "§cDelete Home"
    val selector = "§7Selector"

     val playerManager = PlayerManager()

    private val colorMap = mapOf(
        "§cRed" to Material.RED_DYE,
        "§6Orange" to Material.ORANGE_DYE,
        "§eYellow" to Material.YELLOW_DYE,
        "§aLime" to Material.LIME_DYE,
        "§9Light blue" to Material.LIGHT_BLUE_DYE
    )

    fun openHomeMenu(player: Player, cursePosition: Int = 9) {
        val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString()) ?: return
        val homes = mcPlayer.homeManager.homes ?: return
        val inventory = player.server.createInventory(null, 36, inventoryName)

        for (home in homes) {
            val material = colorMap[home.name] ?: Material.WHITE_DYE
            val displayName = home.name
            val item = ItemStack(material)
            val meta = item.itemMeta
            if (meta != null) {
                meta.setDisplayName(displayName)
                item.itemMeta = meta
            }
            inventory.addItem(item)
        }

        val newItem = ItemStack(Material.DIAMOND_BLOCK)
        val newMeta = newItem.itemMeta
        newMeta?.setDisplayName(createNewHomeName)
        newItem.itemMeta = newMeta
        inventory.setItem(8, newItem)

        val infoItem = ItemStack(Material.IRON_INGOT)
        val infoMeta = infoItem.itemMeta
        infoMeta?.setDisplayName(infoItemName)
        infoMeta?.setCustomModelData(3)
        infoItem.itemMeta = infoMeta
        inventory.setItem(27, infoItem)

        val teleportItem = ItemStack(Material.IRON_INGOT)
        val teleportMeta = teleportItem.itemMeta
        teleportMeta?.setDisplayName(teleportHomeName)
        teleportMeta?.setCustomModelData(1)
        teleportItem.itemMeta = teleportMeta
        inventory.setItem(31, teleportItem)

        val deleteItem = ItemStack(Material.IRON_INGOT)
        val deleteMeta = deleteItem.itemMeta
        deleteMeta?.setDisplayName(deleteHomeName)
        deleteMeta?.setCustomModelData(2)
        deleteItem.itemMeta = deleteMeta
        inventory.setItem(35, deleteItem)

        if (homes.isNotEmpty()) {
            val curserItem = ItemStack(Material.IRON_INGOT)
            val curserItemMeta = curserItem.itemMeta
            curserItemMeta?.setDisplayName(selector)
            curserItemMeta?.setCustomModelData(4)
            curserItem.itemMeta = curserItemMeta
            inventory.setItem(cursePosition, curserItem)
        }

        player.openInventory(inventory)
    }

    fun createHome(player: Player) {
        val location = player.location
        val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString()) ?: return
        val uuID = player.uniqueId.toString()
        val currentHomes = mcPlayer.homeManager.homes

        if ((currentHomes?.size ?: return) < 5) {
            val locationString = mcPlayer.homeManager.locationToString(location)
            val newHome = HomeData(playerUUID = uuID, locationString = locationString, name = getUnusedColor(currentHomes) ?: return)
            mcPlayer.homeManager.addHome(newHome)
        } else {
            player.sendMessage("Du darfst nur 5 Homes haben!")
        }
        openHomeMenu(player)
    }

    fun deleteHome(player: Player, homeName: String) {
        val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString()) ?: return
        val home = getHomeByPlayerAndHomeName(player.uniqueId.toString(), homeName)

        if (home != null) {
            mcPlayer.homeManager.homes?.remove(home)
            openHomeMenu(player)
        } else {
            player.sendMessage("Home '$homeName' nicht gefunden.")
        }
    }

    fun getHomeAboutCursor(inventory: Inventory, player: Player): HomeData? {
        for (i in 0 until inventory.size) {
            val item = inventory.getItem(i)
            if (item?.itemMeta?.displayName == selector) {
                val homeItem = inventory.getItem(i - 9)
                val homeName = homeItem?.itemMeta?.displayName ?: continue
                return getHomeByPlayerAndHomeName(player.uniqueId.toString(), homeName)
            }
        }
        return null
    }

    private fun getHomeByPlayerAndHomeName(playerUUID: String, homeName: String): HomeData? {
        val mcPlayer = playerManager.getMcPlayerByUUID(playerUUID) ?: return null
        return mcPlayer.homeManager.homes?.firstOrNull { it.name.equals(homeName, ignoreCase = true) }
    }

    private fun getUnusedColor(homes: List<HomeData>): String? {
        val usedColors = homes.map { it.name.capitalize() }
        val allColors = listOf("§cRed", "§6Orange", "§eYellow", "§aLime", "§9Light blue")
        return allColors.firstOrNull { it !in usedColors }
    }
}
