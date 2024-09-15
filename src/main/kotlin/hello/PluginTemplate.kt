package hello


import hello.SpringApplication
import hello.minecraft.DefaultPlayer
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.runApplication
import hello.minecraft.AdminGui.AdminGuiCommand
import hello.minecraft.AdminGui.gui.AdminGuiListener
import hello.minecraft.Bossbar.WorldBossBarManager
import hello.minecraft.ClickPlayerAndChat.ChatListener
import hello.minecraft.ClickPlayerAndChat.ClickPlayerListener
import hello.minecraft.ClickPlayerAndChat.commands.ClickPlayerCommand
import hello.minecraft.ClickPlayerAndChat.gui.ClickPlayerGuiListener
import hello.minecraft.Firework.FireWorkManager
import hello.minecraft.Home.commands.HomeCommand
import hello.minecraft.Home.gui.HomeGuiListener
import hello.minecraft.Money.commands.MoneyCommand
import hello.minecraft.Money.commands.PayCommand
import hello.minecraft.NEEDTOSORT.RepairCommand
import hello.minecraft.Player.PlayerDBListener
import hello.minecraft.Player.rank.RankCommand
import hello.minecraft.Player.rank.RankTabCompleter
import hello.minecraft.Scoreboard.ScoreBoardListener
import hello.minecraft.Tablist.TabListListener
import hello.minecraft.Tablist.TabListManager
import hello.minecraft.World.BuildCommand
import hello.minecraft.World.FarmWorld.RandomLocationManager
import hello.minecraft.World.Spawn.SpawnCommand
import hello.minecraft.World.Spawn.SpawnListener
import hello.minecraft.World.realm.RealmListener
import hello.minecraft.World.realm.commands.RealmCommand
import hello.minecraft.World.realm.commands.RealmCommandTabCompleter
import hello.minecraft.World.realm.commands.RealmsCommand
import hello.minecraft.World.realm.gui.RealmGuiListener
import hello.minecraft.World.realm.gui.RealmGuiManager
import hello.minecraft.customItem.gui.CustomAllMenu
import hello.minecraft.customItem.gui.CustomAllMenuCommand
import hello.minecraft.customItem.logik.CostumPlaceListener
import kotlinx.coroutines.CoroutineDispatcher

import org.bukkit.GameRule
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.event.Listener

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
        //  CoroutineScope(Dispatchers.IO).launch {
        startSpringBootApp()
        //   }

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

        Bukkit.getServer().consoleSender.sendMessage("§6Sergrerever AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV OLIVER")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("MAX LIEBT JASMIN!")

        // Setze den ClassLoader auf den des Plugins
        Thread.currentThread().contextClassLoader = this.javaClass.classLoader

        // Starte die Spring Boot Anwendung
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

    private fun startSpringBootApp() {
        // Setze den ClassLoader auf den des Plugins
        Thread.currentThread().contextClassLoader = this.javaClass.classLoader


        runApplication<SpringApplication>()

    }
}
