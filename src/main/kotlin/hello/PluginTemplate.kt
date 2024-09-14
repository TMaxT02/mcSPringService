package hello


import hello.SpringApplication
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.runApplication

class PluginTemplate : JavaPlugin() {


    override fun onEnable() {
        Bukkit.getServer().consoleSender.sendMessage("§6Sergrerever AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV OLIVER")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV")
        Bukkit.getServer().consoleSender.sendMessage("§6Server AKTIV 2024!!!!XD")
        runApplication<SpringApplication>()
    }
}