package hello.minecraft.World.FarmWorld

import hello.PluginTemplate.Companion.instance
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material

class RandomLocationManager() {
    val locations = mutableListOf<Location>()
    var x = 3

    fun getRandomLocation(): Location {
        val location = locations.first()
        locations.removeAt(0)
        createRandomLocationInFarmWorld()
        return location
    }

    private fun createRandomLocationInFarmWorld() {
        Bukkit.getServer().consoleSender.sendMessage("§6Tps GEsetz ")
        val runnable = Runnable {
            val world = Bukkit.getWorld("farmwelt")
            if (world == null) {
                println("Die Welt 'farmwelt' existiert nicht.")
                return@Runnable
            }

            val random = java.util.Random()
            val minX = -500
            val maxX = 500
            val minZ = -500
            val maxZ = 500
            var location: Location

            while (true) {
                val x = random.nextInt(maxX - minX + 1) + minX
                val z = random.nextInt(maxZ - minZ + 1) + minZ
                val y = world.getHighestBlockYAt(x, z)

                location = Location(world, x.toDouble(), y.toDouble(), z.toDouble())
                val block = world.getBlockAt(location)

                if (block.type == Material.GRASS_BLOCK || block.type == Material.SNOW_BLOCK || block.type == Material.STONE  || block.type == Material.SAND) {
                    break
                }
            }

            locations.add(location)
        }
        FarmWorldManager().async.runTaskAsynchronously(instance, runnable)
    }

    fun create10StartRandomLocation() {
        for (i in 1..10) {
            createRandomLocationInFarmWorld()
        }
    }
}