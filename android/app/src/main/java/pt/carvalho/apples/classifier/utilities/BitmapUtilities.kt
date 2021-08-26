package pt.carvalho.apples.classifier.utilities

import android.graphics.Bitmap
import java.lang.IllegalArgumentException
import kotlin.math.min

@Throws(IllegalArgumentException::class)
fun Bitmap.centerSquareCrop(size: Int) : Bitmap {
    val smallestSide = min(width, height)

    if (smallestSide < size) {
        throw IllegalArgumentException("Expected size of bitmap is bigger than the actual bitmap.")
    }

    return Bitmap.createBitmap(this, width / 2 - height / 2, 0, size, size)
}