package gg.flyte.template.classes

import jakarta.persistence.*


@Entity
@Table(name = "mcplayer")
data class McPlayerEntity(
    @Id
    @Column(name = "uuid")
    val uuid: String,

    @Column(name = "rang")
    var rang: String,

    @Column(name = "geld")
    var geld: Int,

    @OneToMany(mappedBy = "player", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var homes: MutableList<HomeEntity> = mutableListOf(),

    @OneToMany(mappedBy = "ownerUUID", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var buildRealmAllowedList: MutableList<BuildRealmAllowedEntity> = mutableListOf()
)
