package pt.carvalho.apples.classifier.utilities

import android.graphics.BitmapFactory
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.lang.IllegalArgumentException

class BitmapUtilitiesTest {

    @Test(expected = IllegalArgumentException::class)
    fun when_centerSquareCrop_is_called_with_a_negative_number_size_an_exception_is_thrown() {
        assets(SQUARE).use { BitmapFactory.decodeStream(it) }
            .centerSquareCrop(-1)
    }

    @Test
    fun when_centerSquareCrop_is_called_with_a_bigger_size_than_the_squre_bitmap_we_resize_it_instead() {
        val expectedSize = 1024

        runTest(
            testFile = SQUARE,
            expectedFile = "expected/square_resize_$expectedSize.png",
            size = expectedSize
        )
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_image_with_bigger_width_with_a_bigger_size_than_the_bitmap_we_resize_it_instead() {
        val expectedSize = 1024

        runTest(
            testFile = WIDTH,
            expectedFile = "expected/square_resize_$expectedSize.png",
            size = expectedSize
        )
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_image_with_bigger_height_with_a_bigger_size_than_the_bitmap_we_resize_it_instead() {
        val expectedSize = 1024

        runTest(
            testFile = HEIGHT,
            expectedFile = "expected/square_resize_$expectedSize.png",
            size = expectedSize
        )
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_square_image_with_500_size_we_crop_it_from_the_center() {
        val expectedSize = 500

        runTest(
            testFile = SQUARE,
            expectedFile = "expected/square_crop_$expectedSize.png",
            size = expectedSize
        )
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_square_image_with_800_size_we_crop_it_from_the_center() {
        val expectedSize = 800

        runTest(
            testFile = SQUARE,
            expectedFile = "expected/square_crop_$expectedSize.png",
            size = expectedSize
        )
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_image_with_bigger_width_we_crop_it_from_the_center() {
        val expectedSize = 1000

        runTest(
            testFile = WIDTH,
            expectedFile = SQUARE,
            size = expectedSize
        )
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_image_with_bigger_height_we_crop_it_from_the_center() {
        val expectedSize = 1000

        runTest(
            testFile = HEIGHT,
            expectedFile = SQUARE,
            size = expectedSize
        )
    }

    @Test
    fun rotate_rotates_bitmap_correctly() {
        val bitmap = assets(ARROW_LEFT).use { BitmapFactory.decodeStream(it) }
        val expectedBitmap = assets(ARROW_RIGHT).use { BitmapFactory.decodeStream(it) }

        val result = bitmap.rotate(180f)

        assertTrue(result.sameAs(expectedBitmap))
    }

    private fun runTest(
        testFile: String,
        expectedFile: String,
        size: Int
    ) {
        val bitmap = assets(testFile).use { BitmapFactory.decodeStream(it) }
        val expectedBitmap = assets(expectedFile).use { BitmapFactory.decodeStream(it) }

        val result = bitmap.centerSquareCrop(size)

        assertTrue(result.sameAs(expectedBitmap))
        assertTrue(result.width == size)
        assertTrue(result.height == size)
    }

    companion object {
        internal const val SQUARE = "sample/square_test.png"
        internal const val WIDTH = "sample/width_test.png"
        internal const val HEIGHT = "sample/height_test.png"
        internal const val ARROW_LEFT = "sample/arrow_left.png"
        internal const val ARROW_RIGHT = "expected/arrow_right.png"
    }
}
