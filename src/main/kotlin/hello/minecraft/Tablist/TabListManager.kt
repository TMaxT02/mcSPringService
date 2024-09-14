import gg.flyte.template.DefaultPlayer
import gg.flyte.template.PluginTemplate
import gg.flyte.template.PluginTemplate.Companion.instance
import gg.flyte.template.Classes.Rang
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

class TabListManager() {
    private val playerManager = PlayerManager()
    fun setPlayerList(player: Player) {
        player.setPlayerListHeaderFooter(
            DefaultPlayer.scoreboardOben,
            DefaultPlayer.scoreboardUnten +
                    "\n\n§f§lSpieler Online: §7" + Bukkit.getOnlinePlayers().size + "/100"
        )
    }

    fun setPlayerTeams(player: Player) {
        val scoreboard = player.scoreboard

        var spieler: Team? = scoreboard.getTeam("12Spieler")
        if (spieler == null) {
            spieler = scoreboard.registerNewTeam("12Spieler")
        }

        var administrator: Team? = scoreboard.getTeam("03Administrator")
        if (administrator == null) {
            administrator = scoreboard.registerNewTeam("03Administrator")
        }

        var developer: Team? = scoreboard.getTeam("02Developer")
        if (developer == null) {
            developer = scoreboard.registerNewTeam("02Developer")
        }

        var mod: Team? = scoreboard.getTeam("04mod")
        if (mod == null) {
            mod = scoreboard.registerNewTeam("04mod")
        }

        var media: Team? = scoreboard.getTeam("07Media")
        if (media == null) {
            media = scoreboard.registerNewTeam("07Media")
        }

        var builder: Team? = scoreboard.getTeam("06Builder")
        if (builder == null) {
            builder = scoreboard.registerNewTeam("06Builder")
        }

        var helper: Team? = scoreboard.getTeam("05Helper")
        if (helper == null) {
            helper = scoreboard.registerNewTeam("05Helper")
        }

        var geld: Team? = scoreboard.getTeam("Geld")
        if (geld == null) {
            geld = scoreboard.registerNewTeam("Geld")
        }

        var star: Team? = scoreboard.getTeam("09Star")
        if (star == null) {
            star = scoreboard.registerNewTeam("09Star")
        }

        var sun: Team? = scoreboard.getTeam("10Sun")
        if (sun == null) {
            sun = scoreboard.registerNewTeam("10Sun")
        }

        var shine: Team? = scoreboard.getTeam("11Shine")
        if (shine == null) {
            shine = scoreboard.registerNewTeam("11Shine")
        }

        var owner: Team? = scoreboard.getTeam("01owner")
        if (owner == null) {
            owner = scoreboard.registerNewTeam("01owner")
        }

        administrator.prefix = Rang.Admin.symbole
        spieler.prefix = Rang.Spieler.symbole
        developer.prefix = Rang.Developer.symbole
        mod.prefix = Rang.Mod.symbole
        media.prefix = Rang.Media.symbole
        builder.prefix = Rang.Builder.symbole
        helper.prefix = Rang.Helper.symbole
        geld.prefix = Rang.Geld.symbole
        star.prefix = Rang.Star.symbole
        sun.prefix = Rang.Sun.symbole
        shine.prefix = Rang.Shine.symbole
        owner.prefix = Rang.Owner.symbole

        for (target in Bukkit.getOnlinePlayers()) {
            val mcPlayer = playerManager.getMcPlayerByUUID(target.uniqueId.toString())
            if (mcPlayer?.rang == Rang.Admin) {
                administrator.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Spieler) {
                spieler.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Developer) {
                developer.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Mod) {
                mod.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Media) {
                media.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Builder) {
                builder.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Helper) {
                helper.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Geld) {
                geld.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Star) {
                star.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Sun) {
                sun.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Shine) {
                shine.addEntry(target.name)
            }
            if (mcPlayer?.rang == Rang.Owner) {
                owner.addEntry(target.name)
            }
        }
    }

    fun updateScoreBoardForAllPlayer() {
        val async = instance.server.scheduler
        val runnable = Runnable {
            Thread.sleep(50)
            val runnableSync = Runnable {
                for (target in Bukkit.getOnlinePlayers()) {
                    PluginTemplate.instance.tabListManager.setPlayerList(target)
                    PluginTemplate.instance.tabListManager.setPlayerTeams(target)
                }
            }
            async.runTaskAsynchronously(instance, runnableSync)

        }
        async.runTaskAsynchronously(instance, runnable)
    }
}
