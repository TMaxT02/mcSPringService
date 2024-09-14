import gg.flyte.template.Home.gui.HomeGuiManager
import gg.flyte.template.Player.McPlayerApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class PlayerDBListener(private val plugin: JavaPlugin) : Listener {
    private val homeGuiManager = HomeGuiManager()
    private val playerManager = PlayerManager()

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val player = event.player
        CoroutineScope(Dispatchers.IO).launch {

          McPlayerApiClient.checkAndCreatePlayer(player)
        }
    }
}