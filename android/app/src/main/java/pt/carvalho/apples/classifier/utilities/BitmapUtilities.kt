package pt.carvalho.apples.classifier.utilities

import android.graphics.Bitmap
import java.lang.IllegalArgumentException
import kotlin.math.min

@Throws(IllegalArgumentException::class)
internal fun Bitmap.centerSquareCrop(size: Int): Bitmap {
    val smallestSide = min(width, height)

    if (size < 0) {
        throw IllegalArgumentException("Expected size of bitmap is bigger than the actual bitmap.")
    }

    val expectedSize = if (smallestSide < size) smallestSide else size
    val x = (width - expectedSize) / 2
    val y = (height - expectedSize) / 2

    val resizedBitmap = Bitmap.createBitmap(this, x, y, expectedSize, expectedSize)

    return if (smallestSide < size) {
        Bitmap.createScaledBitmap(resizedBitmap, size, size, false)
    } else {
        resizedBitmap
    }
}
