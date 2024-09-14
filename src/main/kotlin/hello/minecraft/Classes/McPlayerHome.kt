package gg.flyte.template.Classes

import gg.flyte.template.Classes.Data.HomeData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

class McPlayerHome(
    val homes: MutableList<HomeData>?,
) {
    fun addHome(homeToAdd: HomeData) {
        homes?.add(homeToAdd)
    }

    fun removeHome(homeToRemove: HomeData) {
        homes?.removeIf { it.playerUUID == homeToRemove.playerUUID && it.name == homeToRemove.name }
    }

    fun teleportToHome(homeName: String, player: Player) {
        val home = homes?.find { it.name == homeName } ?: return
        player.teleport(getLocation(home.locationString))
    }

    private fun getLocation(locationString: String): Location {
        return stringToLocation(locationString)
    }

     fun stringToLocation(locationString: String): Location {
        val parts = locationString.split(",")
        if (parts.size != 4) {
            throw IllegalArgumentException("Invalid location string: $locationString")
        }
        val world = Bukkit.getWorld(parts[0]) ?: throw IllegalArgumentException("World not found: ${parts[0]}")
        val x = parts[1].toDoubleOrNull() ?: throw IllegalArgumentException("Invalid x coordinate: ${parts[1]}")
        val y = parts[2].toDoubleOrNull() ?: throw IllegalArgumentException("Invalid y coordinate: ${parts[2]}")
        val z = parts[3].toDoubleOrNull() ?: throw IllegalArgumentException("Invalid z coordinate: ${parts[3]}")
        return Location(world, x, y, z)
    }

     fun locationToString(location: Location): String {
        val worldName = location.world?.name ?: throw IllegalArgumentException("World cannot be null")
        val x = location.x
        val y = location.y
        val z = location.z
        return "$worldName,$x,$y,$z"
    }
}
