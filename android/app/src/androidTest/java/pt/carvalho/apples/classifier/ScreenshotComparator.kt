package pt.carvalho.apples.classifier

/**
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Based of: https://github.com/googlecodelabs/android-compose-codelabs/blob/main/TestingCodelab/app/src/androidTest/java/com/example/compose/rally/ScreenshotComparator.kt
 * Modified to not exactly match both bitmaps(cause depending on the gpu there could be minor changes) by André Carvalho
 */
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.jvm.Throws
import kotlin.math.abs

/**
 * This class represents a screenshot test case.
 *
 * @property folder the test case folder path.
 * @property filename the screenshot filename that's in [folder].
 * @property maxErrorPercentage the error threshold we should accept. This must be a value between 0 and 100 (since it represents a percentage).
 * If the difference between the sourced image and the captured image is bigger or equal than this value an [AssertionError] is thrown.
 */
internal data class TestCase(
    val folder: String,
    val filename: String = "",
    val maxErrorPercentage: Float = 0.5f
) {
    fun safeFolderPathname(): String = folder.replace("/", "_")
}

object ScreenshotComparator {
    internal fun clean(
        testCase: TestCase
    ) {
        with(File(filesDir, testCase.folder)) {
            deleteRecursively()
        }
    }

    /**
     * Simple on-device screenshot comparator that uses golden images present in
     * `androidTest/assets`.
     *
     * Minimum SDK is O. Densities between devices must match.
     *
     * Screenshots are saved on device in `/data/data/{package}/files`.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(IOException::class, AssertionError::class)
    internal fun assertScreenshotMatches(
        testCase: TestCase,
        node: SemanticsNodeInteraction
    ) {
        val bitmap = node.captureToImage().asAndroidBitmap()

        // Save screenshot to file for debugging
        saveScreenshot(
            folder = testCase.folder,
            filename = "${testCase.safeFolderPathname()}_${testCase.filename}_${System.currentTimeMillis()}",
            bitmap = bitmap
        )

        val expectedBitmap = assets("${testCase.folder}/${testCase.filename}.png")
            .use { BitmapFactory.decodeStream(it) }

        val difference = expectedBitmap.compare(bitmap)

        if (difference >= testCase.maxErrorPercentage) {
            throw AssertionError("Sizes match but bitmap content has differences. ($difference% different).")
        }
    }
}

private fun saveScreenshot(
    folder: String,
    filename: String,
    bitmap: Bitmap
) {
    val path = File(filesDir, folder).also {
        if (!it.exists()) {
            it.mkdirs()
        }
    }

    FileOutputStream("$path/$filename.png").use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    println("Saved screenshot to $path/$filename.png.")
}

/**
 * Modified to expect the bitmap to be similar but not 100% identical.
 * (Different GPUs don't generate the same image 100%)
 * Mostly inspired from https://rosettacode.org/wiki/Percentage_difference_between_images#Kotlin
 */
private fun Bitmap.compare(other: Bitmap): Float {
    if (this.width != other.width || this.height != other.height) {
        throw AssertionError("Size of screenshot does not match golden file (check device density).")
    }

    val row1 = IntArray(width)
    val row2 = IntArray(width)

    var difference = 0L

    for (column in 0 until height) {
        this.getRow(row1, column)
        other.getRow(row2, column)

        for (row in 0 until width) {
            difference += row1[row].pixelDifference(row2[row])
        }
    }

    val maxDifference = 3L * 255 * width * height

    return 100.0f * difference / maxDifference
}

private fun Int.pixelDifference(other: Int): Int {
    val red = ((this shr 16) and 0xff) to ((other shr 16) and 0xff)
    val green = ((this shr 8) and 0xff) to ((other shr 8) and 0xff)
    val blue = (this and 0xff) to (other and 0xff)

    return abs(red.first - red.second) +
        abs(green.first - green.second) +
        abs(blue.first - blue.second)
}

private fun Bitmap.getRow(pixels: IntArray, column: Int) {
    this.getPixels(pixels, 0, width, 0, column, width, 1)
}
