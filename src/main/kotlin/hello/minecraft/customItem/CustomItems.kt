import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object CustomItems {

    val NEXT_BUTTON = createItemStack(8007, "Next Page")
    val NEXT_BUTTON_DIM = createItemStack(8008, "Next Page")
    val BACK_BUTTON = createItemStack(8009, "Previous Page")
    val BACK_BUTTON_DIM = createItemStack(8010, "Previous Page")


    private fun createItemStack(customModelData: Int, displayName: String): ItemStack {
        val itemStack = ItemStack(Material.PAPER) // Beispiel: Material.PAPER, ändere es nach Bedarf
        val meta: ItemMeta = itemStack.itemMeta ?: throw IllegalStateException("ItemMeta cannot be null")
        meta.setCustomModelData(customModelData)
        meta.setDisplayName(displayName)
        itemStack.itemMeta = meta
        return itemStack
    }
}