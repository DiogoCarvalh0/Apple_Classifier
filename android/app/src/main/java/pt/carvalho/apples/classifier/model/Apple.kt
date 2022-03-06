package pt.carvalho.apples.classifier.model

internal data class Apple(
    val name: String,
    val description: String,
    val confidence: Int,
    val picture: String,
    val origin: String
)

internal val SAMPLE = Apple(
    name = "Fugi",
    description = "The Fuji apple is an apple cultivar developed by growers at Tohoku Research Station in Fujisaki" +
        ", Aomori, Japan, in the late 1930s, and brought to market in 1962.",
    confidence = 50,
    picture = "https://i.imgur.com/dn08P0z.png",
    origin = "Portugal"
)
