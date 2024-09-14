package gg.flyte.template.Player

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import com.fasterxml.jackson.databind.SerializationFeature
import gg.flyte.template.Classes.McPlayer
import gg.flyte.template.Classes.McPlayerBuildRealm
import gg.flyte.template.Classes.McPlayerHome
import gg.flyte.template.Classes.Rang
import io.ktor.client.call.*
import io.ktor.serialization.jackson.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object McPlayerApiClient {

    private val client = HttpClient {
        install(ContentNegotiation) {
            jackson {
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            }
        }
    }

    suspend fun getPlayerById(uuid: String): McPlayer? {
        return try {
            val response: HttpResponse = client.get("http://localhost:8081/players/$uuid")
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createPlayer(player: McPlayer): McPlayer? {
        return try {
            val response: HttpResponse = client.post("http://localhost:8081/players") {
                contentType(ContentType.Application.Json)
                setBody(player)
            }
            if (response.status == HttpStatusCode.Created) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updatePlayer(uuid: String, updatedPlayer: McPlayer): McPlayer? {
        return try {
            val response: HttpResponse = client.put("http://localhost:8081/players/$uuid") {
                contentType(ContentType.Application.Json)
                setBody(updatedPlayer)
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deletePlayer(uuid: String): Boolean {
        return try {
            val response: HttpResponse = client.delete("http://localhost:8081/players/$uuid")
            response.status == HttpStatusCode.NoContent
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun checkAndCreatePlayer(player: Player) {
        val uuid = player.uniqueId.toString()
        val existingPlayer = getPlayerById(uuid)
        val ich = Bukkit.getPlayer("immerluck")
        ich?.sendMessage("oliver ${existingPlayer?.playerUUID}")
        if (existingPlayer == null) {
            val mcPlayer: McPlayer = McPlayer(
                uuid, Rang.Spieler, 100, McPlayerHome(mutableListOf()),
                McPlayerBuildRealm(
                    mutableListOf()
                )
            )
            createPlayer(mcPlayer)
        }
    }
}
