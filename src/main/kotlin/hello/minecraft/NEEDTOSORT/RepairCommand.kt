package gg.flyte.template.NEEDTOSORT

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.ItemFrame
import org.bukkit.entity.Player

class RepairCommand : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be executed by a player.")
            return true
        }
        for (world: World in Bukkit.getWorlds()) {
            for (entity: Entity in world.entities) {
                if (entity is ItemFrame) {
                    val itemFrame: ItemFrame = entity
                    val location = itemFrame.location
                    val item = itemFrame.item
                    val rotation = itemFrame.rotation

                    // Remove the old ItemFrame
                    itemFrame.remove()

                    // Create a new ItemFrame at the same location
                    val newFrame = world.spawn(location, ItemFrame::class.java)
                    newFrame.isVisible = false

                    // Set the fixed property for the new ItemFrame
                    newFrame.setFixed(true)

                    // Set other properties for the new ItemFrame
                    newFrame.isInvulnerable = true
                    newFrame.rotation = rotation
                    newFrame.setFacingDirection(itemFrame.facing)
                    newFrame.setItem(item)
                }
            }
        }
        return true
    }
}
