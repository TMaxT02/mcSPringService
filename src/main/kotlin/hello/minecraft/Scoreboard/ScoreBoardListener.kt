import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class ScoreBoardListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun joinMessageListener(event: PlayerJoinEvent) {
        val player = event.player
        ScoreboardManager().createScoreboard(player)
    }
}
