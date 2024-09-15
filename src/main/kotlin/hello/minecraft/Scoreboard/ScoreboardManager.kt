package hello.minecraft.Scoreboard
import hello.minecraft.Player.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Score
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.ScoreboardManager

class ScoreboardManager {
    private val playerManager = PlayerManager()
    private val scoreboardManager: ScoreboardManager = Bukkit.getScoreboardManager()

    fun createScoreboard(player: Player) {
        val scoreboard: Scoreboard = scoreboardManager.newScoreboard
        val objective: Objective = scoreboard.registerNewObjective("stats", "dummy", "§b§lBlockTopia ")

        objective.displaySlot = DisplaySlot.SIDEBAR

        val money = playerManager.getMcPlayerByUUID(player.uniqueId.toString())?.geld
        val emptyTop: Score = objective.getScore(" ")
        val playtime: Score = objective.getScore("§lSpielzeit:")
        val playtimeValue: Score = objective.getScore("§e0 Stunden ")
        val empty1: Score = objective.getScore("")
        val kirstalle: Score = objective.getScore("§lKristalle:")
        val kirstalleValue: Score = objective.getScore("§b$money §r楓")

        emptyTop.score = 0
        playtime.score = -2
        playtimeValue.score = -3
        empty1.score = -4
        kirstalle.score = -5
        kirstalleValue.score = -6

        player.scoreboard = scoreboard
    }

    fun updateScoreboard(player: Player) {

        val money = playerManager.getMcPlayerByUUID(player.uniqueId.toString())?.geld // Nehme an, dass "geld" das Geld des Spielers darstellt
        val scoreboard: Scoreboard = player.scoreboard
        val objective: Objective = scoreboard.getObjective("stats") ?: return // Überprüfen, ob das Scoreboard oder das Objective vorhanden sind

        val oldMoneyEntry = scoreboard.getEntries().find { it.endsWith("楓") } // Suche nach dem alten Geldwert

        // Alten Eintrag entfernen, wenn vorhanden
        if (oldMoneyEntry != null) {
            scoreboard.resetScores(oldMoneyEntry)
        }

        // Neuen Geldwert hinzufügen
        val kirstalleValue = objective.getScore("§b$money §r楓")
        kirstalleValue.score = -5
    }
}
