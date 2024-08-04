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
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class McPlayerControllerTest(
  @Autowired private val context: WebApplicationContext,
  @Autowired private val mcPlayerService: McPlayerService,
  @Autowired private val homeService: HomeService,
  @Autowired private val buildRealmAllowedService: BuildRealmAllowedService
) {

  private lateinit var mockMvc: MockMvc

  private val existingPlayer1 = McPlayerEntity("player1-uuid", Rang.Admin, 1200)
  private val player1home1 = HomeDTO(1, "player1-uuid", "player1home1", "player1loc1")
  private val player1home2 = HomeDTO(2, "player1-uuid", "player1home2", "player1loc2")
  private val player1buildRealm1 = BuildRealmAllowedDTO(1, "player1-uuid", "o1")
  private val player1buildRealm2 = BuildRealmAllowedDTO(2, "player1-uuid", "o2")

  private val existingPlayer2 = McPlayerEntity("player2-uuid", Rang.Star, 100)
  private val player2home1 = HomeDTO(3, "player2-uuid", "player2home1", "player2loc1")
  private val player2home2 = HomeDTO(4, "player2-uuid", "player2home2", "player2loc2")
  private val player2Realm1 = BuildRealmAllowedDTO(3, "player2-uuid", "03")
  private val player2Realm2 = BuildRealmAllowedDTO(4, "player2-uuid", "04")

  @BeforeEach
  fun setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

    mcPlayerService.saveMcPlayer(existingPlayer1)
    homeService.createHome(player1home1)
    homeService.createHome(player1home2)
    buildRealmAllowedService.createBuildRealmAllowed(player1buildRealm1)
    buildRealmAllowedService.createBuildRealmAllowed(player1buildRealm2)

    mcPlayerService.saveMcPlayer(existingPlayer2)
    homeService.createHome(player2home1)
    homeService.createHome(player2home2)
    buildRealmAllowedService.createBuildRealmAllowed(player2Realm1)
    buildRealmAllowedService.createBuildRealmAllowed(player2Realm2)
  }

  @Test
  fun `should return all player details when player1 exists`() {
    val url = "/players/player1-uuid"

    mockMvc.perform(
      MockMvcRequestBuilders.get(url)
        .accept(MediaType.APPLICATION_JSON)
    )
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.playerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.rang").value("Admin"))
      .andExpect(jsonPath("$.geld").value(1200))
      .andExpect(jsonPath("$.homes[0].homeid").value(1))
      .andExpect(jsonPath("$.homes[0].playerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.homes[0].name").value("player1home1"))
      .andExpect(jsonPath("$.homes[0].locationString").value("player1loc1"))
      .andExpect(jsonPath("$.homes[1].homeid").value(2))
      .andExpect(jsonPath("$.homes[1].playerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.homes[1].name").value("player1home2"))
      .andExpect(jsonPath("$.homes[1].locationString").value("player1loc2"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].buildRealmAllowedID").value(1))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].playerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].otherPlayerUUID").value("o1"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].buildRealmAllowedID").value(2))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].playerUUID").value("player1-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].otherPlayerUUID").value("o2"))
  }

  @Test
  fun `should return all player details when player2 exists`() {
    val url = "/players/player2-uuid"

    mockMvc.perform(
      MockMvcRequestBuilders.get(url)
        .accept(MediaType.APPLICATION_JSON)
    )
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.rang").value("Star"))
      .andExpect(jsonPath("$.geld").value(100))
      .andExpect(jsonPath("$.homes[0].homeid").value(3))
      .andExpect(jsonPath("$.homes[0].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.homes[0].name").value("player2home1"))
      .andExpect(jsonPath("$.homes[0].locationString").value("player2loc1"))
      .andExpect(jsonPath("$.homes[1].homeid").value(4))
      .andExpect(jsonPath("$.homes[1].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.homes[1].name").value("player2home2"))
      .andExpect(jsonPath("$.homes[1].locationString").value("player2loc2"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].buildRealmAllowedID").value(3))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].otherPlayerUUID").value("03"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].buildRealmAllowedID").value(4))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].otherPlayerUUID").value("04"))
  }

  @Test
  fun `should create a new player`() {
    val requestJson = """{
        "playerUUID": "new-player-uuid",
        "rang": "Builder",
        "geld": 600,
        "homes": [],
        "buildRealmAllowedList": []
    }"""

    mockMvc.perform(
      post("/players")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)
    )
      .andExpect(status().isCreated) // Changed to isCreated
      .andExpect(jsonPath("$.playerUUID").value("new-player-uuid"))
      .andExpect(jsonPath("$.rang").value("Builder"))
      .andExpect(jsonPath("$.geld").value(600))
      .andExpect(jsonPath("$.homes").isEmpty)
      .andExpect(jsonPath("$.buildRealmAllowedList").isEmpty)
  }

  @Test
  fun `should update an existing player`() {
    val playerId = "player2-uuid"
    val updateJson = """{
        "playerUUID": "player2-uuid",
        "rang": "Admin",
        "geld": 1200,
        "homes": [
            {
                "homeid": 3,
                "playerUUID": "player2-uuid",
                "name": "player2home1",
                "locationString": "player2loc1"
            },
            {
                "homeid": 4,
                "playerUUID": "player2-uuid",
                "name": "player2home2",
                "locationString": "player2loc2"
            }
        ],
        "buildRealmAllowedList": [
            {
                "buildRealmAllowedID": 1,
                "playerUUID": "player2-uuid",
                "otherPlayerUUID": "03"
            },
            {
                "buildRealmAllowedID": 2,
                "playerUUID": "player2-uuid",
                "otherPlayerUUID": "04"
            }
        ]
    }"""

    mockMvc.perform(
      MockMvcRequestBuilders.put("/players/$playerId")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateJson)
    )
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.rang").value("Admin"))
      .andExpect(jsonPath("$.geld").value(1200))
      .andExpect(jsonPath("$.homes[0].homeid").value(3))
      .andExpect(jsonPath("$.homes[0].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.homes[0].name").value("player2home1"))
      .andExpect(jsonPath("$.homes[0].locationString").value("player2loc1"))
      .andExpect(jsonPath("$.homes[1].homeid").value(4))
      .andExpect(jsonPath("$.homes[1].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.homes[1].name").value("player2home2"))
      .andExpect(jsonPath("$.homes[1].locationString").value("player2loc2"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].buildRealmAllowedID").value(1))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[0].otherPlayerUUID").value("03"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].buildRealmAllowedID").value(2))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].playerUUID").value("player2-uuid"))
      .andExpect(jsonPath("$.buildRealmAllowedList[1].otherPlayerUUID").value("04"))
  }

  @Test
  fun `should delete a player`() {
    val url = "/players/player1-uuid"

    mockMvc.perform(
      MockMvcRequestBuilders.delete(url)
        .accept(MediaType.APPLICATION_JSON)
    )
      .andDo(MockMvcResultHandlers.print())
      .andExpect(status().isNoContent)
  }
}
