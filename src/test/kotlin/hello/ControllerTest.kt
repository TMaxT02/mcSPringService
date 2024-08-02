package hello

import hello.classen.dto.BuildRealmAllowedDTO
import hello.classen.dto.HomeDTO
import hello.classen.entity.McPlayerEntity
import hello.classen.Rang
import hello.logic.BuildRealmAllowedService
import hello.logic.HomeService
import hello.logic.McPlayerService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@Sql(scripts = ["classpath:schema.sql"])
class ControllerTest(
  @Autowired private val mcPlayerService: McPlayerService,
  @Autowired private val homeService: HomeService,
  @Autowired private val buildRealmAllowedService: BuildRealmAllowedService,
  @Autowired private val context: WebApplicationContext
) {
  private lateinit var mockMvc: MockMvc

  @BeforeEach
  fun setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

    val player1 = McPlayerEntity(
      uuid = "player1-uuid",
      rang = Rang.Admin,
      geld = 1000
    )
    mcPlayerService.saveMcPlayer(player1)

    val home1Player1 = HomeDTO("home1-1", "player1-uuid", "Home1", "Location1", "player1-uuid")
    val home2Player1 = HomeDTO("home1-2", "player1-uuid", "Home2", "Location2", "player1-uuid")
    val buildRealmAllowed1Player1 = BuildRealmAllowedDTO("0", "player1-uuid", "other-player1-uuid")
    val buildRealmAllowed2Player1 = BuildRealmAllowedDTO("0", "player1-uuid", "other-player2-uuid")

    homeService.createHome(home1Player1)
    homeService.createHome(home2Player1)
    buildRealmAllowedService.createBuildRealmAllowed(buildRealmAllowed1Player1)
    buildRealmAllowedService.createBuildRealmAllowed(buildRealmAllowed2Player1)

    val player2 = McPlayerEntity(
      uuid = "player2-uuid",
      rang = Rang.Builder,
      geld = 500
    )
    mcPlayerService.saveMcPlayer(player2)

    val home1Player2 = HomeDTO("home2-1", "player2-uuid", "Home3", "Location3", "player2-uuid")
    val home2Player2 = HomeDTO("home2-2", "player2-uuid", "Home4", "Location4", "player2-uuid")
    val buildRealmAllowed1Player2 = BuildRealmAllowedDTO("0", "player2-uuid", "other-player3-uuid")
    val buildRealmAllowed2Player2 = BuildRealmAllowedDTO("0", "player2-uuid", "other-player4-uuid")

    homeService.createHome(home1Player2)
    homeService.createHome(home2Player2)
    buildRealmAllowedService.createBuildRealmAllowed(buildRealmAllowed1Player2)
    buildRealmAllowedService.createBuildRealmAllowed(buildRealmAllowed2Player2)
  }

  @Test
  fun `should return all player details when player exists`() {
    val url = "/players/player1-uuid"

    mockMvc.perform(MockMvcRequestBuilders.get(url))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.uuid").value("player1-uuid"))
      .andExpect(jsonPath("$.rang").value("Admin"))
      .andExpect(jsonPath("$.geld").value(1000))
      .andExpect(jsonPath("$.homes[0].homeid").value("home1-1"))
      .andExpect(jsonPath("$.homes[0].uuid").value("player1-uuid"))
      .andExpect(jsonPath("$.homes[0].name").value("Home1"))
      .andExpect(jsonPath("$.homes[0].locationString").value("Location1"))
      .andExpect(jsonPath("$.homes[1].homeid").value("home1-2"))
      .andExpect(jsonPath("$.homes[1].uuid").value("player1-uuid"))
      .andExpect(jsonPath("$.homes[1].name").value("Home2"))
      .andExpect(jsonPath("$.homes[1].locationString").value("Location2"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].ownerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].otherPlayerUUID").value("other-player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].ownerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].otherPlayerUUID").value("other-player2-uuid"))
  }

  @Test
  fun `should create a new player`() {
    val url = "/players"
    val requestJson = """{
            "uuid": "new-uuid",
            "rang": "Builder",
            "geld": 500,
            "homes": [
                {
                    "homeid": "home3-1",
                    "uuid": "new-uuid",
                    "name": "Home5",
                    "locationString": "Location5",
                    "player": "new-uuid"
                },
                {
                    "homeid": "home3-2",
                    "uuid": "new-uuid",
                    "name": "Home6",
                    "locationString": "Location6",
                    "player": "new-uuid"
                }
            ],
            "buildRealmAllowedList": [
                {
                    "id": 0,
                    "ownerUUID": "new-uuid",
                    "otherPlayerUUID": "other-player5-uuid"
                },
                {
                    "id": 0,
                    "ownerUUID": "new-uuid",
                    "otherPlayerUUID": "other-player6-uuid"
                }
            ]
        }"""

    mockMvc.perform(MockMvcRequestBuilders.post(url)
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestJson))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isCreated)
      .andExpect(jsonPath("$.uuid").value("new-uuid"))
      .andExpect(jsonPath("$.rang").value("Builder"))
      .andExpect(jsonPath("$.geld").value(500))
      .andExpect(jsonPath("$.homes[0].homeid").value("home3-1"))
      .andExpect(jsonPath("$.homes[0].uuid").value("new-uuid"))
      .andExpect(jsonPath("$.homes[0].name").value("Home5"))
      .andExpect(jsonPath("$.homes[0].locationString").value("Location5"))
      .andExpect(jsonPath("$.homes[1].homeid").value("home3-2"))
      .andExpect(jsonPath("$.homes[1].uuid").value("new-uuid"))
      .andExpect(jsonPath("$.homes[1].name").value("Home6"))
      .andExpect(jsonPath("$.homes[1].locationString").value("Location6"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].ownerUUID").value("new-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].otherPlayerUUID").value("other-player5-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].ownerUUID").value("new-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].otherPlayerUUID").value("other-player6-uuid"))
  }

  @Test
  fun `should update an existing player`() {
    val updatedPlayer = McPlayerEntity(
      uuid = "player1-uuid",
      rang = Rang.Developer,
      geld = 2000
    )
    val url = "/players/player1-uuid"
    val requestJson = """{
            "uuid": "player1-uuid",
            "rang": "Developer",
            "geld": 2000,
            "homes": [
                {
                    "homeid": "home1-1",
                    "uuid": "player1-uuid",
                    "name": "Home1",
                    "locationString": "Location1",
                    "player": "player1-uuid"
                },
                {
                    "homeid": "home1-2",
                    "uuid": "player1-uuid",
                    "name": "Home2",
                    "locationString": "Location2",
                    "player": "player1-uuid"
                }
            ],
            "buildRealmAllowedList": [
                {
                    "id": 0,
                    "ownerUUID": "player1-uuid",
                    "otherPlayerUUID": "other-player1-uuid"
                },
                {
                    "id": 0,
                    "ownerUUID": "player1-uuid",
                    "otherPlayerUUID": "other-player2-uuid"
                }
            ]
        }"""

    mockMvc.perform(MockMvcRequestBuilders.put(url)
      .contentType(MediaType.APPLICATION_JSON)
      .content(requestJson))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.uuid").value("player1-uuid"))
      .andExpect(jsonPath("$.rang").value("Developer"))
      .andExpect(jsonPath("$.geld").value(2000))
      .andExpect(jsonPath("$.homes[0].homeid").value("home1-1"))
      .andExpect(jsonPath("$.homes[0].uuid").value("player1-uuid"))
      .andExpect(jsonPath("$.homes[0].name").value("Home1"))
      .andExpect(jsonPath("$.homes[0].locationString").value("Location1"))
      .andExpect(jsonPath("$.homes[1].homeid").value("home1-2"))
      .andExpect(jsonPath("$.homes[1].uuid").value("player1-uuid"))
      .andExpect(jsonPath("$.homes[1].name").value("Home2"))
      .andExpect(jsonPath("$.homes[1].locationString").value("Location2"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].ownerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].otherPlayerUUID").value("other-player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].ownerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].otherPlayerUUID").value("other-player2-uuid"))
  }

  @Test
  fun `should delete a player`() {
    val url = "/players/player1-uuid"

    mockMvc.perform(MockMvcRequestBuilders.delete(url))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isNoContent)
  }
}
