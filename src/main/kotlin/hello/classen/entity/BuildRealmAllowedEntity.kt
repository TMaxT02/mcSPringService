package hello.classen.entity

import hello.classen.dto.BuildRealmAllowedDTO
import jakarta.persistence.*

@Entity
@Table(name = "buildrealmallowed")
data class BuildRealmAllowedEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  val id: Long? = null,

  @Column(name = "ownerUUID", nullable = false)
  var ownerUUID: String,

  @Column(name = "otherPlayerUUID", nullable = false)
  var otherPlayerUUID: String
) {
  fun toDTO(): BuildRealmAllowedDTO {
    return BuildRealmAllowedDTO(
      id = (this.id ?: throw IllegalStateException("ID should not be null")),
      ownerUUID = this.ownerUUID,
      otherPlayerUUID = this.otherPlayerUUID
    )
  }
}
