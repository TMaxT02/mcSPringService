package gg.flyte.template

import org.bukkit.Bukkit
import org.bukkit.Location

object AllLocation {
    val spawn = Location(Bukkit.getWorld("world"), 0.5, 66.2, 0.5).apply { pitch = 0f; yaw = 135f }
    val story = Location(Bukkit.getWorld("story"), 0.0, -58.0, 0.0).apply { pitch = 0f; yaw = 135f }
    val adminArea = Location(Bukkit.getWorld("world"), 32.0, 59.0, -12.0).apply { pitch = 0f; yaw = 135f }
    val islandTemplate: Location = Location(Bukkit.getWorld("template"), 416.0, 73.5, 201.0)
}

