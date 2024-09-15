package hello.minecraft.customItem.logik
import hello.minecraft.Player.PlayerManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

class CostumPlaceListener(private val plugin: JavaPlugin) : Listener {
    val costumPlaceManager = CostumPlaceManager()
    var playerManager = PlayerManager()

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val clickedBlock = event.clickedBlock ?: return
        if (event.action == Action.RIGHT_CLICK_BLOCK && clickedBlock.type == Material.NOTE_BLOCK) {
            val player = event.player
            val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
            val blockFace = event.blockFace
            if (mcPlayer?.playerAllowedToBuildOnSpawn() ?: return) {
                costumPlaceManager.placeBlockOnNoteBlock(player, clickedBlock, blockFace)
            }
            event.isCancelled = true
        }
    }

    @EventHandler
    fun placeCustomItemFrame(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val clickedBlock = event.clickedBlock ?: return
        if (event.player.inventory.itemInMainHand.type != Material.STICK) return
        val player = event.player

        event.isCancelled = true
        val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
        if (mcPlayer?.playerAllowedToBuildOnSpawn() ?: return) {
            costumPlaceManager.placeItemFrame(event.player, clickedBlock, event.blockFace)
        }
    }

    @EventHandler //allow to build on an noteblock
    fun placeCustomNoteBLock(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_BLOCK && event.player.inventory.itemInMainHand.type == Material.PAPER) {
            event.isCancelled = true
            val player = event.player
            val clickedBlock = event.clickedBlock ?: return
            val blockFace = event.blockFace
            val mcPlayer = playerManager.getMcPlayerByUUID(player.uniqueId.toString())
            if (mcPlayer?.playerAllowedToBuildOnSpawn() ?: return) {
                costumPlaceManager.placeNoteBlock(player, clickedBlock, blockFace)
            }

        }
    }

    @EventHandler // prevent left click
    fun preventNoteBlockInteraction(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            val block = event.clickedBlock ?: return
            if (block.type == Material.NOTE_BLOCK) {
                event.isCancelled = true
            }
        }
    }
    @EventHandler // prevent left click
    fun disableNote(event: NotePlayEvent) {
     event.isCancelled = true
    }
    @EventHandler(ignoreCancelled = true) // prevent noteblock update
    fun onBlockPhysics(event: BlockPhysicsEvent) {
        val block = event.block
        val aboveBlock = block.location.add(0.0, 1.0, 0.0).block
        val underBlock = block.location.add(0.0, -1.0, 0.0).block
        val blockType = block.type
        val aboveBlockType = aboveBlock.type
        val underBlockType = underBlock.type

        // Handle Notenblock und übergeordnete Notenblöcke
        if (blockType == Material.NOTE_BLOCK || aboveBlockType == Material.NOTE_BLOCK || underBlockType == Material.NOTE_BLOCK) {
            var currentBlock = if (blockType == Material.NOTE_BLOCK) block else aboveBlock
            while (currentBlock.type == Material.NOTE_BLOCK) {
                currentBlock.state.update(true, true)
                currentBlock = currentBlock.location.add(0.0, 1.0, 0.0).block
            }
            event.isCancelled = true
        } else if (!blockType.toString().lowercase().contains("sign")) {
            block.state.update(true, false)
        }
    }
}