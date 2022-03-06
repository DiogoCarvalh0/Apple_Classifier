package pt.carvalho.apples.classifier.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import pt.carvalho.apples.classifier.utilities.AssetsReader

internal class DatabaseTest {

    private val assetsReader = mock<AssetsReader>()

    private val type = Types.newParameterizedType(MutableList::class.java, Apple::class.java)
    private val adapter: JsonAdapter<List<Apple>> = Moshi.Builder().build().adapter(type)

    private val database = DatabaseImpl(
        adapter = adapter,
        assetsReader = assetsReader
    )

    @Test
    fun `we load database from json on 1st query`() = runBlocking {
        whenever(assetsReader.read(any())).thenReturn(DATA_JSON)

        val result = database.findLabel(EXISTING_LABEL)
        assertEquals(DATA.first(), result)
    }

    @Test
    fun `when we dont have a label in the database we return null`() = runBlocking {
        whenever(assetsReader.read(any())).thenReturn(DATA_JSON)

        val result = database.findLabel(ERROR_LABEL)
        assertNull(result)
    }

    companion object {
        const val EXISTING_LABEL = "Fuji"
        const val ERROR_LABEL = "Error"

        const val DATA_JSON = "[" +
            "{" +
            "   \"id\":\"Fuji\"," +
            "   \"name\":\"Fuji\"," +
            "   \"description\":\"\"," +
            "   \"image\":\"\"," +
            "   \"location\":{\n" +
            "       \"name\":\"Japan\"" +
            "   }" +
            "}" +
        "]"

        val DATA = listOf(
            Apple(
                id = EXISTING_LABEL,
                name = EXISTING_LABEL,
                description = "",
                image = "",
                origin = Apple.Location(
                    name = "Japan"
                )
            )
        )
    }
}
