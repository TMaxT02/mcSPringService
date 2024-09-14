package gg.flyte.template

import AdminGuiCommand
import gg.flyte.template.AdminGui.gui.AdminGuiListener
import ClickPlayerListener
import gg.flyte.template.customItem.gui.CustomAllMenu
import CostumPlaceListener
import gg.flyte.template.ClickPlayerAndChat.ChatListener
import gg.flyte.template.Home.commands.HomeCommand
import gg.flyte.template.Home.gui.HomeGuiListener
import PlayerDBListener
import ScoreBoardListener
import gg.flyte.template.World.realm.RealmListener
import gg.flyte.template.NEEDTOSORT.RepairCommand
import gg.flyte.template.World.Spawn.SpawnListener
import TabListManager
import gg.flyte.template.Bossbar.WorldBossBarManager
import gg.flyte.template.ClickPlayerAndChat.commands.ClickPlayerCommand
import gg.flyte.template.ClickPlayerAndChat.gui.ClickPlayerGuiListener
import gg.flyte.template.Firework.FireWorkManager
import gg.flyte.template.World.Spawn.SpawnCommand
import gg.flyte.template.Money.commands.MoneyCommand
import gg.flyte.template.Money.commands.PayCommand
import gg.flyte.template.Player.rank.RankCommand
import gg.flyte.template.Player.rank.RankTabCompleter
import gg.flyte.template.World.FarmWorld.RandomLocationManager
import gg.flyte.template.World.BuildCommand
import gg.flyte.template.Tablist.TabListListener
import gg.flyte.template.World.realm.RealmCommand
import gg.flyte.template.World.realm.RealmsCommand
import gg.flyte.template.World.realm.commands.RealmCommandTabCompleter
import gg.flyte.template.World.realm.gui.RealmGuiListener
import gg.flyte.template.World.realm.gui.RealmGuiManager
import gg.flyte.template.customItem.gui.CustomAllMenuCommand
import hello.spring.SpringApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.runApplication
import java.io.File
import java.io.IOException
import java.net.URLClassLoader
import kotlin.coroutines.CoroutineContext

class PluginTemplate : JavaPlugin() {
    lateinit var tabListManager: TabListManager

    companion object {
        val realmGuiManager = RealmGuiManager()
        lateinit var instance: PluginTemplate
        val main = object : CoroutineDispatcher() {
            override fun dispatch(context: CoroutineContext, block: Runnable) {
                Bukkit.getScheduler().runTask(instance, block)
            }
        }
    }

    fun registerEvent(listener: Listener) {
        server.pluginManager.registerEvents(listener, instance)
    }

    override fun onEnable() {
        CoroutineScope(Dispatchers.IO).launch {
            startSpringBootApp()
        }

        instance = this
        tabListManager = TabListManager()
        server.pluginManager.registerEvents(PlayerDBListener(instance), instance)
        getCommand("rank")?.tabCompleter = RankTabCompleter()
        getCommand("rank")?.setExecutor(RankCommand())
        getCommand("spawn")?.setExecutor(SpawnCommand())
        getCommand("repair")?.setExecutor(RepairCommand())
        getCommand("money")?.setExecutor(MoneyCommand())
        getCommand("pay")?.setExecutor(PayCommand())
        getCommand("home")?.setExecutor(HomeCommand(this))

        getCommand("realm")?.setExecutor(RealmCommand())
        getCommand("realm")?.tabCompleter = RealmCommandTabCompleter()
        getCommand("realms")?.setExecutor(RealmsCommand())

        getCommand("remove")?.setExecutor(RealmCommand())
        getCommand("build")?.setExecutor(BuildCommand())
        getCommand("adminmenu")?.setExecutor(AdminGuiCommand())
        getCommand("click")?.setExecutor(ClickPlayerCommand())


        getCommand("org")?.setExecutor(CustomAllMenuCommand())
        registerEvent(CustomAllMenu(instance))

        registerEvent(PlayerDBListener(instance))
        registerEvent(RealmListener(instance))
        registerEvent(AdminGuiListener(instance))
        registerEvent(ChatListener(instance))
        registerEvent(ScoreBoardListener(instance))
        registerEvent(TabListListener(instance))
        registerEvent(SpawnListener(instance))
        registerEvent(HomeGuiListener(instance))
        registerEvent(ClickPlayerListener(instance))
        registerEvent(ClickPlayerGuiListener(instance))
        registerEvent(RealmGuiListener())
        gameRules()
        spawnEntitysOnSpawn()
        RandomLocationManager().create10StartRandomLocation()
        FireWorkManager().startSunriseSunsetTask()
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        WorldBossBarManager().startBossBarThread()


        registerEvent(CostumPlaceListener(instance))

        getBlockStrength(Material.BARRIER)

        // Setze den ClassLoader auf den des Plugins
        Thread.currentThread().contextClassLoader = this.javaClass.classLoader

        // Starte die Spring Boot Anwendung
        runApplication<SpringApplication>()
        runApplication<SpringApplication>()

    }

    override fun onDisable() {
        removeEntityOnSpawn()
        WorldBossBarManager().removeAllBossBars()
    }

    private fun gameRules() {
        val spawn = Bukkit.getWorld("world")
        spawn?.setGameRule(GameRule.KEEP_INVENTORY, false)
        spawn?.setGameRule(GameRule.DO_FIRE_TICK, false)
        spawn!!.setGameRule(GameRule.FALL_DAMAGE, false)
    }

    private fun spawnEntitysOnSpawn() {
        val world = Bukkit.getWorld("world")
        val armorStand = world?.spawnEntity(DefaultPlayer.spawnPNG, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.isVisible = false
        armorStand.customName = "界"
        armorStand.isCustomNameVisible = true
    }

    private fun removeEntityOnSpawn() {
        val spawn = Bukkit.getWorld("world")
        spawn?.entities?.filter { it.type == EntityType.ARMOR_STAND }?.forEach { it.remove() }
    }

    fun getBlockStrength(block: Any) {
        Bukkit.getServer().consoleSender.sendMessage("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
        Bukkit.getServer().consoleSender.sendMessage(block.javaClass.fields.joinToString { it.name })
        Bukkit.getServer().consoleSender.sendMessage("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

    }

    fun startSpringBootApp() {
        // Verzeichnis des Minecraft-Plugins
        val pluginDirectory = File("plugins") // Ersetze "plugins" falls notwendig

        // Pfad zur JAR-Datei
        val jarPath = File(pluginDirectory, "paperspring.jar").absolutePath

        // Lade die JAR-Datei
        val url = File(jarPath).toURI().toURL()
        val classLoader = URLClassLoader(arrayOf(url), this::class.java.classLoader)

        // Lade die SpringApplication-Klasse
        val springApplicationClass = classLoader.loadClass("hello.SpringApplication") // Ersetze mit dem vollständigen Klassennamen

        // Starte die Spring Boot-Anwendung
        val mainMethod = springApplicationClass.getMethod("main", Array<String>::class.java)
        mainMethod.invoke(null, arrayOf<String>()) // Optionale Argumente hinzufügen
    }
}