package gg.flyte.template.Classes.Data

data class HomeData(
    val homeid: Int?= null,
    val playerUUID: String,
    val name: String,
    var locationString: String,
)