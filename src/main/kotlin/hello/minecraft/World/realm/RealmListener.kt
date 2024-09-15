package hello.minecraft.World.realm


import hello.minecraft.Player.PlayerManager
import hello.minecraft.World.FarmWorld.FarmWorldManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin

class RealmListener(private val plugin: JavaPlugin) : Listener {
    private val playerManager = PlayerManager()
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (RealmWorldManager().hasPlayerWorld(player)) {
        } else {
            RealmWorldManager().createWorldForPlayer(player)
        }
    }

    @EventHandler
    fun playerOnFarmTp(event: PlayerMoveEvent) {
        val farmx = 1
        val farmz = -15
        if (event.player.world.name == "world" && event.player.location.x.toInt() == farmx && event.player.location.z.toInt() == farmz) {
          FarmWorldManager().rtpToFarmWelt(event.player)
        }
    }
}
















