package hello.minecraft.NEEDTOSORT

import org.bukkit.inventory.ItemStack

fun updateItemName(item: ItemStack, newName: String): ItemStack {
    val updatedItem = item.clone()
    val meta = updatedItem.itemMeta
    if (meta != null) {
        meta.setDisplayName(newName)
        updatedItem.itemMeta = meta
    }
    return updatedItem
}