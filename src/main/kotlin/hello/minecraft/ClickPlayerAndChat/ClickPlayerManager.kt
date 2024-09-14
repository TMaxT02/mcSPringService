package gg.flyte.template.ClickPlayerAndChat

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class ClickPlayerManager {
    val inventoryName = "§fPlayer: "

    fun createClickableMessage(sender: Player, message: String) {
        val textComponent = TextComponent(message)
        textComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clickmessage ${sender.name}")
        textComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("${message}").create())


        // Nachricht an alle Spieler senden
        Bukkit.getOnlinePlayers().forEach { recipient ->
            recipient.spigot().sendMessage(textComponent)
        }
    }
}
