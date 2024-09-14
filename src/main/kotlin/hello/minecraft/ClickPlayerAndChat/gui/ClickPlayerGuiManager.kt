package gg.flyte.template.ClickPlayerAndChat.gui

import gg.flyte.template.NEEDTOSORT.updateItemName
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class ClickPlayerGuiManager {
    val inventoryName = "§fPlayer: "
    val helmetName = "Helmet"
    val chestName = "Chest"
    val leggingsName = "Leggings"
    val bootsName = "Boots"
    val sworldName = "Sword"
    val realmName = "Realm"
    val allowPlayerToBuildName = "Allow player to build on your realm"
    val sendOneCristalname = "Send one crystal"

    fun openVirtualChest(player: Player, target: Player) {
        val virtualChest: Inventory = Bukkit.createInventory(null, 45, inventoryName + "§6${target.displayName}")

        // Setze Items in den entsprechenden Slots
        val targetInventory = target.inventory

        targetInventory.helmet?.let { virtualChest.setItem(1, updateItemName(it, helmetName)) } // Slot 2 (Index 1)
        targetInventory.chestplate?.let { virtualChest.setItem(10, updateItemName(it, chestName)) } // Slot 11 (Index 10)
        targetInventory.leggings?.let { virtualChest.setItem(19, updateItemName(it, leggingsName)) } // Slot 20 (Index 19)
        targetInventory.boots?.let { virtualChest.setItem(28, updateItemName(it, bootsName)) } // Slot 29 (Index 28)
        targetInventory.itemInMainHand?.let { virtualChest.setItem(37, updateItemName(it, sworldName)) } // Slot 38 (Index 37)

        // Spieler Kopf
        val playerHead = ItemStack(Material.PLAYER_HEAD)
        val headMeta = playerHead.itemMeta as SkullMeta
        headMeta.owningPlayer = target
        headMeta.setDisplayName("§6${target.name}") // Setze DisplayName richtig
        playerHead.itemMeta = headMeta
        virtualChest.setItem(16, updateItemName(playerHead, target.name)) // Slot 17 (Index 16)

        // Papier mit benutzerdefiniertem Namen und Custom Model Data
        val paper = ItemStack(Material.IRON_INGOT)
        virtualChest.setItem(13, updateItemName(paper, realmName)) // Slot 14 (Index 13)

        // Kuchen mit benutzerdefiniertem Namen in Türkis
        val cake = ItemStack(Material.CAKE)
        virtualChest.setItem(31, updateItemName(cake, allowPlayerToBuildName)) // Slot 32 (Index 31)

        // Goldbarren mit benutzerdefiniertem Namen in Gelb
        val goldIngot = ItemStack(Material.GOLD_INGOT)
        virtualChest.setItem(34, updateItemName(goldIngot, sendOneCristalname)) // Slot 35 (Index 34)

        // Äußere Kanten mit grauen Glasscheiben füllen, nur wenn der Slot leer ist
        for (row in 0..4) {
            for (col in 0..8) {
                val index = row * 9 + col
                if (row == 0 || row == 4 || col == 0 || col == 8) {
                    if (virtualChest.getItem(index) == null) {
                        val glassPane = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
                        virtualChest.setItem(index, updateItemName(glassPane, "")) // Leeren Namen setzen
                    }
                }
            }
        }

        // Virtuelles Inventar für den Spieler öffnen
        player.openInventory(virtualChest)
    }
}
