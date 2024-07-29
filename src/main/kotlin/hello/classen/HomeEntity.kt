package hello.classen


import jakarta.persistence.*

@Entity
@Table(name = "homes")
data class HomeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homeid")
    val homeid: String? = null,

    @Column(name = "uuid")
    val uuid: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "location")
    var locationString: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
    val player: McPlayerEntity? = null
)
