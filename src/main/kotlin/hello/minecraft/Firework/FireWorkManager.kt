package hello.minecraft.Firework

import hello.PluginTemplate.Companion.instance
import hello.minecraft.AllLocation
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
import org.bukkit.scheduler.BukkitRunnable

class FireWorkManager () {
    private val ListOfAllColors = listOf(Color.RED, Color.ORANGE, Color.YELLOW, Color.BLUE, Color.OLIVE, Color.GREEN, Color.AQUA)
    fun startSunriseSunsetTask() {

        object : BukkitRunnable() {
            override fun run() {
                val world = Bukkit.getWorld("world")
                val time = world?.time ?: return
                if (time > 17900 && time < 18100) {
                    fireworkCircle(AllLocation.spawn)
                }
            }
        }.runTaskTimer(instance, 0L, 200L) // Schedule the task to run every 20 ticks (1 second)
    }
    private fun spawnSingleFireWork(
        location: Location,
        colors: List<Color>,
        type: FireworkEffect.Type,
        delayToStart: Long,
    ) {
        Bukkit.getScheduler().runTaskLater(instance, Runnable {
            val firework =
                Bukkit.getWorlds()[0].spawnEntity(
                    location.clone().add(0.0, 15.0, 0.0),
                    EntityType.FIREWORK_ROCKET
                ) as Firework
            val meta = firework.fireworkMeta

            val effect = FireworkEffect.builder()
                .with(type)
                .withColor(colors)
                .flicker(false)
                .trail(true)
                .build()

            meta.addEffect(effect)
            meta.power = 1
            firework.fireworkMeta = meta
            firework.detonate() // Hier wird das Feuerwerk sofort explodieren
        }, delayToStart)
    }

    private fun fireworkCircle(center: Location) {
        val radius = 45
        val steps = 25
        val angleStep = 2 * Math.PI / steps
        var height = 0
        var direction = 1 // 1 für aufwärts, -1 für abwärts
        var count = 0

        for (i in 0 until steps) {
            val angle = i * angleStep
            val x = center.x + radius * Math.cos(angle)
            val z = center.z + radius * Math.sin(angle)

            val spawnLocation = Location(center.world, x, center.y + 10 + height, z)

            spawnSingleFireWork(
                spawnLocation,
                ListOfAllColors,
                FireworkEffect.Type.BALL_LARGE,
                delayToStart = i * 20L / 4
            )

            count++
            if (count == 3) { // Ändere die Richtung alle 3 Feuerwerke
                direction *= -1
                count = 0
            }

            height += 10 * direction
        }
    }
}