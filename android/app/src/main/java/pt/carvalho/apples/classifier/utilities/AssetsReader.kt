package pt.carvalho.apples.classifier.utilities

import android.content.Context
import java.io.IOException
import java.io.InputStream

internal interface AssetsReader {
    fun read(file: String): String
}

internal class AssetsReaderImpl(
    private val context: Context
) : AssetsReader {

    @Throws(IOException::class)
    override fun read(file: String): String {
        var input: InputStream? = null

        return try {
            input = context.assets.open(file)

            input.bufferedReader().readText()
        } finally {
            input?.close()
        }
    }
}
