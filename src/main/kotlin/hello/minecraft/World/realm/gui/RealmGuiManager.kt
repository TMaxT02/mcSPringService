package gg.flyte.template.World.realm.gui

import PlayerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.lang3.ObjectUtils.Null
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta


class RealmGuiManager {
    private val playerManager = PlayerManager()
    private val fakePlayers = mutableListOf<OfflinePlayer>()

    private val playerPageMap = mutableMapOf<Player, Int>()

    init {
        addFakePlayers()
    }

    fun openRealmChest(player: Player, page: Int) {
        val pages = createPagedInventories()
        if (page < 0 || page >= pages.size) return

        if (pages[page].getItem(43) == null) {
            player.sendMessage("last page !")
        }
        player.openInventory(pages[page])
        playerPageMap[player] = page
    }

    fun openNextPage(player: Player) {
        val currentPage = playerPageMap[player] ?: 0
        val pages = createPagedInventories()
        val newPage = (currentPage + 1).coerceAtMost(pages.size - 1)

        openRealmChest(player, newPage)
    }

    fun openPreviousPage(player: Player) {
        val currentPage = playerPageMap[player] ?: 0
        val newPage = (currentPage - 1).coerceAtLeast(0)
        openRealmChest(player, newPage)
    }

    fun addFakePlayers() {
        for (i in 1..70) {
            CoroutineScope(Dispatchers.Default).launch {
                val fakePlayer = Bukkit.getOfflinePlayer("FakePlayer$i")
                fakePlayers.add(fakePlayer)
                Bukkit.getPlayer("immerluck")?.sendMessage("FakePlayer$i erstellt")
            }
        }
    }

    private fun createPagedInventories(): MutableList<Inventory> {
        val inventories = mutableListOf<Inventory>()
        var inventory = createNewInventory()
        var slotIndex = 19

        for (fakePlayer in fakePlayers) {
            if (slotIndex > 25 && slotIndex < 28) slotIndex = 28
            if (slotIndex > 34 && slotIndex < 37) slotIndex = 37
            if (slotIndex > 43) {
                inventories.add(inventory)
                inventory = createNewInventory()
                slotIndex = 19
            }
            val skull = createPlayerHead(fakePlayer)
            inventory.setItem(slotIndex, skull)
            slotIndex++
        }

        if (slotIndex > 19) {
            inventories.add(inventory)
        }

        inventories.forEachIndexed { index, inv ->
            if (index > 0) {
                inv.setItem(46, CustomItems.BACK_BUTTON)
            } else {
                inv.setItem(46, CustomItems.BACK_BUTTON_DIM)
            }
            if (index < inventories.size - 1) {
                inv.setItem(52, CustomItems.NEXT_BUTTON)
            } else {
                inv.setItem(52, CustomItems.NEXT_BUTTON_DIM)
            }
        }

        return inventories
    }

    private fun createNewInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, 54, "${b(4)}${ChatColor.WHITE}섩§3§lRealms")
        return inventory
    }

    private fun createPlayerHead(player: OfflinePlayer): ItemStack {
        val skull = ItemStack(Material.PLAYER_HEAD)
        val meta = skull.itemMeta as SkullMeta
        meta.owningPlayer = player
        meta.setDisplayName(player.name ?: "Unknown Player")
        skull.itemMeta = meta
        return skull
    }

    fun b(n: Int = 1): String {
        return "\uF001".repeat(n)
    }
}