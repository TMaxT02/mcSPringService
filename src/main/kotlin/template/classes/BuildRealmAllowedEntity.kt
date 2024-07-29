package gg.flyte.template.classes

import jakarta.persistence.*


@Entity
@Table(name = "buildrealmallowed")
data class BuildRealmAllowedEntity(
    @Column(name = "ownerUUID")
    val ownerUUID: String,

    @Column(name = "otherPlayerUUID")
    val otherPlayerUUID: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String? = null
)
