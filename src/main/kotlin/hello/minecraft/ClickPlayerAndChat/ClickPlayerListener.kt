import gg.flyte.template.ClickPlayerAndChat.gui.ClickPlayerGuiManager
import gg.flyte.template.World.realm.RealmWorldManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.plugin.java.JavaPlugin

class ClickPlayerListener(private val plugin: JavaPlugin) : Listener {
    private val clickPlayerGuiManager = ClickPlayerGuiManager()
    private val realmWorldManager = RealmWorldManager()

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEntityEvent) {
        if (event.rightClicked is Player) {
            val player = event.player
            val target = event.rightClicked as Player
            clickPlayerGuiManager.openVirtualChest(player, target)
        }
    }
}