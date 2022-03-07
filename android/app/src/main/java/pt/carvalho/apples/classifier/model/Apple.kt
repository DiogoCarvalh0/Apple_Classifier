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
    description = "Crisp and very juicy with a sugary-sweet flavor, making it ultra refreshing.",
    confidence = 62,
    picture = "https://i.imgur.com/dn08P0z.png",
    origin = "Fujisaki, Japan \uD83C\uDDEF\uD83C\uDDF5"
)
