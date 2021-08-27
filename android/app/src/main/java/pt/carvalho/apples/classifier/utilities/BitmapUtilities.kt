package pt.carvalho.apples.classifier.utilities

import android.graphics.Bitmap
import java.lang.IllegalArgumentException
import kotlin.math.min

@Throws(IllegalArgumentException::class)
fun Bitmap.centerSquareCrop(size: Int): Bitmap {
    val smallestSide = min(width, height)

    if (smallestSide < size || size < 0) {
        throw IllegalArgumentException("Expected size of bitmap is bigger than the actual bitmap.")
    }

    val x = (width - size) / 2
    val y = (height - size) / 2

    return Bitmap.createBitmap(this, x, y, size, size)
}
