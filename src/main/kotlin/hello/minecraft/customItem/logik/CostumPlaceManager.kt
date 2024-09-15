package hello.minecraft.customItem.logik


import hello.minecraft.Classes.NoteBlockMapManager
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Rotation
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class CostumPlaceManager {
    private val recentActions = mutableListOf<RecentAction>()
    private val noteBlockMapManager = NoteBlockMapManager()
    private val cooldown = 100L

    data class RecentAction(val playerId: String, val timestamp: Long)

    fun placeItemFrame(player: Player, clickedBlock: Block, blockFace: BlockFace) {
        val playerId = player.uniqueId.toString()
        val currentTime = System.currentTimeMillis()
        recentActions.removeAll { currentTime - it.timestamp > cooldown }

        if (recentActions.any { it.playerId == playerId }) return

        recentActions.add(RecentAction(playerId, currentTime))
        val world = player.world
        val newLocation = calculateNewLocation(clickedBlock, blockFace)
        val itemFrameRotation = playerDirectionToItemFrameRotation(getPlayerDirection(player.yaw))
        val itemFrame = world.spawn(newLocation, ItemFrame::class.java)
        itemFrame.setItem(ItemStack(Material.CANDLE), false)
        itemFrame.setFacingDirection(blockFace)
        itemFrame.setRotation(itemFrameRotation)
        itemFrame.isInvulnerable = true
        itemFrame.setGravity(false)
        itemFrame.isFixed = true
        newLocation.block.type = Material.BARRIER
    }

    fun placeNoteBlock(player: Player, clickedBlock: Block, blockFace: BlockFace) {
        val playerId = player.uniqueId.toString()
        val currentTime = System.currentTimeMillis()

        // Entferne veraltete Aktionen
        recentActions.removeAll { currentTime - it.timestamp > cooldown }

        // Überprüfe, ob der Spieler in der Abkühlzeit ist
        if (recentActions.any { it.playerId == playerId }) {
            return
        }
        recentActions.add(RecentAction(playerId, currentTime))
        val newLocation = calculateNewLocation(clickedBlock, blockFace)
        if (isLocationMatchingPlayerArea(player, newLocation)) return

        newLocation.block.type = Material.NOTE_BLOCK
        // Hole die Notenblock-Daten
        val stats =
            noteBlockMapManager.getPairByCustomModelData(player.inventory.itemInMainHand.itemMeta.customModelData)
                ?: return
        val noteBlockData = newLocation.block.blockData as NoteBlock
        noteBlockData.instrument = stats.first
        noteBlockData.note = stats.second
        newLocation.block.blockData = noteBlockData
        player.playSound(player.location, org.bukkit.Sound.BLOCK_PACKED_MUD_PLACE, 1.0f, 1.0f)
    }

    fun placeBlockOnNoteBlock(player: Player, clickedBlock: Block, blockFace: BlockFace) {
        val itemInHand = player.inventory.itemInMainHand
        if (itemInHand.type == Material.AIR) return

        if (clickedBlock.type != Material.NOTE_BLOCK) return

        val newLocation = calculateNewLocation(clickedBlock, blockFace)
        newLocation.block.type = itemInHand.type
        newLocation.block.blockData = itemInHand.type.createBlockData()
        if (player.gameMode == GameMode.CREATIVE) return
        val inventory = player.inventory
        if (itemInHand.amount > 1) {
            itemInHand.amount -= 1
        } else {
            inventory.setItemInMainHand(ItemStack(Material.AIR))
        }
    }

    private fun calculateNewLocation(clickedBlock: Block, blockFace: BlockFace): Location {
        val newLocation = clickedBlock.location.clone()
        when (blockFace) {
            BlockFace.UP -> newLocation.add(0.0, 1.0, 0.0)
            BlockFace.SOUTH -> newLocation.add(0.0, 0.0, 1.0)
            BlockFace.WEST -> newLocation.add(-1.0, 0.0, 0.0)
            BlockFace.NORTH -> newLocation.add(0.0, 0.0, -1.0)
            BlockFace.EAST -> newLocation.add(1.0, 0.0, 0.0)
            BlockFace.DOWN -> newLocation.add(0.0, -1.0, 0.0)
            else -> newLocation.add(0.0, 0.0, 0.0)
        }
        return newLocation
    }


    private fun getPlayerDirection(yaw: Float): BlockFace {
        return when {
            yaw in -22.5..22.5 -> BlockFace.SOUTH
            yaw in 22.5..67.5 -> BlockFace.SOUTH_WEST
            yaw in 67.5..112.5 -> BlockFace.WEST
            yaw in 112.5..157.5 -> BlockFace.NORTH_WEST
            yaw in 157.5..180.0 -> BlockFace.NORTH
            yaw in -180.0..-157.5 -> BlockFace.NORTH
            yaw in -157.5..-112.5 -> BlockFace.NORTH_EAST
            yaw in -112.5..-67.5 -> BlockFace.EAST
            yaw in -67.5..-22.0 -> BlockFace.SOUTH_EAST
            else -> BlockFace.NORTH
        }
    }

    private fun playerDirectionToItemFrameRotation(x: BlockFace): Rotation {
        return when (x) {
            BlockFace.NORTH -> Rotation.FLIPPED
            BlockFace.EAST -> Rotation.COUNTER_CLOCKWISE
            BlockFace.SOUTH -> Rotation.NONE
            BlockFace.WEST -> Rotation.CLOCKWISE
            BlockFace.SOUTH_EAST -> Rotation.COUNTER_CLOCKWISE_45
            BlockFace.SOUTH_WEST -> Rotation.CLOCKWISE_45
            BlockFace.NORTH_WEST -> Rotation.CLOCKWISE_135
            BlockFace.NORTH_EAST -> Rotation.FLIPPED_45
            else -> Rotation.FLIPPED
        }
    }

    fun isLocationMatchingPlayerArea(player: Player, location: Location): Boolean {
        // Blockkoordinaten der Location
        val locX = location.x.toInt()
        val locY = location.y.toInt()
        val locZ = location.z.toInt()

        // Blockkoordinaten der Spielerposition (Füße)
        val playerBlockX = player.location.x.toInt()
        val playerBlockY = player.location.y.toInt()
        val playerBlockZ = player.location.z.toInt()

        // Blockkoordinaten der Augenposition des Spielers
        val eyeBlockX = player.eyeLocation.x.toInt()
        val eyeBlockY = player.eyeLocation.y.toInt()
        val eyeBlockZ = player.eyeLocation.z.toInt()

        // Überprüfen, ob die Koordinaten der Füße oder der Augen des Spielers mit der Location übereinstimmen
        return (locX == playerBlockX && locY == playerBlockY && locZ == playerBlockZ) ||
                (locX == eyeBlockX && locY == eyeBlockY && locZ == eyeBlockZ)
    }
}