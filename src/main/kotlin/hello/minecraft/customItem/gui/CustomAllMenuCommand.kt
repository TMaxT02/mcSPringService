package gg.flyte.template.customItem.gui

import gg.flyte.template.PluginTemplate
import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import org.bukkit.scheduler.BukkitRunnable
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

class CustomAllMenuCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        player.sendMessage(isServerActive().toString())
        givePlayerTrimmedChestplatesOnInterval(player)
        // CustomAllMenu(PluginTemplate.instance).openGui(player,112)
        return true
    }
}

fun givePlayerTrimmedChestplatesOnInterval(player: Player) {
    val trimMaterials = listOf(
        TrimMaterial.QUARTZ, TrimMaterial.IRON, TrimMaterial.NETHERITE,
        TrimMaterial.REDSTONE, TrimMaterial.COPPER, TrimMaterial.GOLD,
        TrimMaterial.EMERALD, TrimMaterial.DIAMOND, TrimMaterial.LAPIS,
        TrimMaterial.AMETHYST
    )

    val trimPatterns = listOf(
        TrimPattern.SENTRY, TrimPattern.DUNE, TrimPattern.COAST, TrimPattern.WILD,
        TrimPattern.WARD, TrimPattern.EYE, TrimPattern.VEX, TrimPattern.TIDE,
        TrimPattern.SNOUT, TrimPattern.RIB, TrimPattern.SPIRE, TrimPattern.WAYFINDER,
        TrimPattern.SHAPER, TrimPattern.SILENCE, TrimPattern.RAISER, TrimPattern.HOST,
        TrimPattern.FLOW, TrimPattern.BOLT
    )

    var index = 0

    CoroutineScope(Dispatchers.IO).launch {

        while (index < trimMaterials.size * trimPatterns.size) {
            delay(1000L)

            val trimMaterial = trimMaterials[index % trimMaterials.size]
            val trimPattern = trimPatterns[index / trimMaterials.size]

            withContext(PluginTemplate.main) {
                val chestplate = ItemStack(Material.LEATHER_CHESTPLATE)
                val meta = chestplate.itemMeta as? ArmorMeta ?: run {
                    player.sendMessage("Fehler beim Erstellen des Item-Metas.")
                    return@withContext
                }
                val trim = ArmorTrim(trimMaterial, trimPattern)
                meta.setTrim(trim)
                meta.setCustomModelData(1)
                chestplate.itemMeta = meta

                player.sendMessage("Versuche, eine Rüstung zu setzen...")
                player.inventory.chestplate = chestplate
            }

            index++
        }
    }

}

fun isServerActive(): Boolean {
    val client = HttpClient(CIO)
    val url = "http://localhost:8081" // Hartkodierte URL

    return try {
        val response: HttpResponse = runBlocking {
            client.get(url) {
                timeout {
                    requestTimeoutMillis = 5000 // 5 Sekunden Timeout
                }
            }
        }
        response.status == HttpStatusCode.OK
    } catch (e: Exception) {
        false
    } finally {
        client.close()
    }
}