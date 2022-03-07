package pt.carvalho.apples.classifier.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import pt.carvalho.apples.classifier.utilities.AssetsReader
import java.io.IOException

@JsonClass(generateAdapter = true)
internal data class Apple(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "flavour")
    val description: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "location")
    val origin: Location
) {
    @JsonClass(generateAdapter = true)
    internal data class Location(
        @Json(name = "name")
        val name: String,
        val points: List<LocationPoint> = emptyList()
    ) {
        @JsonClass(generateAdapter = true)
        internal data class LocationPoint(
            val latitude: Float,
            val longitude: Float
        )
    }
}

internal interface Database {
    suspend fun findLabel(label: String): Apple?
}

private const val DATABASE_FILE = "database.json"

internal class DatabaseImpl(
    private val adapter: JsonAdapter<List<Apple>>,
    private val assetsReader: AssetsReader
) : Database {
    private var data: List<Apple> = emptyList()

    override suspend fun findLabel(label: String): Apple? {
        if (data.isEmpty()) {
            data = loadDatabase()
        }

        return data.firstOrNull { apple -> apple.id == label }
    }

    private fun loadDatabase(): List<Apple> {
        return try {
            val json = assetsReader.read(DATABASE_FILE)

            adapter.fromJson(json) ?: emptyList()
        } catch (e: IOException) { emptyList() }
    }
}
