package hello.minecraft.ClickPlayerAndChat


import hello.minecraft.ClickPlayerAndChat.gui.ClickPlayerGuiManager
import hello.minecraft.Player.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin

class ChatListener(private val plugin: JavaPlugin) : Listener {

    private val clickPlayerGuiManager = ClickPlayerGuiManager()
    private val clickPlayerManager = ClickPlayerManager()
    private val playerManager = PlayerManager()

     @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        event.isCancelled = true

        val sender = event.player
        val message = event.message
        val mcPlayer = playerManager.getMcPlayerByUUID(sender.uniqueId.toString())


        val formattedMessage = "${mcPlayer?.rang?.symbole}${sender.displayName} §7»§r $message"


         clickPlayerManager.createClickableMessage(sender, formattedMessage)
    }

    @EventHandler
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        val command = event.message.removePrefix("/")
        if (command.startsWith("clickmessage ")) {
            val targetName = command.removePrefix("clickmessage ")
            val target = Bukkit.getPlayer(targetName)
            if (target != null) {
                clickPlayerGuiManager.openVirtualChest(player, target)
                event.isCancelled = true
            }
        }
    }
}
