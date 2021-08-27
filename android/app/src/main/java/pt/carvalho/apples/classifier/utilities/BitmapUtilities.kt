package pt.carvalho.apples.classifier.utilities

import android.graphics.Bitmap
import android.graphics.Matrix
import java.lang.IllegalArgumentException
import kotlin.math.min

@Throws(IllegalArgumentException::class)
internal fun Bitmap.centerSquareCrop(size: Int): Bitmap {
    val smallestSide = min(width, height)

    if (size < 0) {
        throw IllegalArgumentException("Expected size of bitmap is bigger than the actual bitmap.")
    }

    val expectedSize = if (smallestSide < size) smallestSide else size

    val croppedBitmap = crop(
        bitmap = this,
        x = (width - expectedSize) / 2,
        y = (height - expectedSize) / 2,
        size = expectedSize
    )

    return if (smallestSide < size) {
        resize(croppedBitmap, size)
    } else {
        croppedBitmap
    }
}

internal fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply {
        postRotate(degrees)
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

private fun crop(
    bitmap: Bitmap,
    x: Int,
    y: Int,
    size: Int
): Bitmap = Bitmap.createBitmap(bitmap, x, y, size, size)

private fun resize(bitmap: Bitmap, size: Int): Bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false)
