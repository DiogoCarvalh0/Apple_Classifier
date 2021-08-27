package pt.carvalho.apples.classifier.utilities

import android.graphics.BitmapFactory
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.lang.IllegalArgumentException

class BitmapUtilitiesTest {

    @Test(expected = IllegalArgumentException::class)
    fun when_centerSquareCrop_is_called_with_a_bigger_size_than_the_bitmap_an_exception_is_thrown() {
        val bitmap = assets(SQUARE).use { BitmapFactory.decodeStream(it) }

        bitmap.centerSquareCrop(1024)
    }

    @Test(expected = IllegalArgumentException::class)
    fun when_centerSquareCrop_is_called_with_a_negative_number_size_an_exception_is_thrown() {
        val bitmap = assets(SQUARE).use { BitmapFactory.decodeStream(it) }

        bitmap.centerSquareCrop(-1)
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_square_image_with_500_size_we_crop_it_from_the_center() {
        val bitmap = assets(SQUARE).use { BitmapFactory.decodeStream(it) }
        val expectedBitmap = assets("expected/square_crop_500.png").use { BitmapFactory.decodeStream(it) }

        val result = bitmap.centerSquareCrop(500)

        assertTrue(result.sameAs(expectedBitmap))
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_square_image_with_800_size_we_crop_it_from_the_center() {
        val bitmap = assets(SQUARE).use { BitmapFactory.decodeStream(it) }
        val expectedBitmap = assets("expected/square_crop_800.png").use { BitmapFactory.decodeStream(it) }

        val result = bitmap.centerSquareCrop(800)

        assertTrue(result.sameAs(expectedBitmap))
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_image_bigger_width_we_crop_it_from_the_center() {
        val bitmap = assets(WIDTH).use { BitmapFactory.decodeStream(it) }
        val expectedBitmap = assets(SQUARE).use { BitmapFactory.decodeStream(it) }

        val result = bitmap.centerSquareCrop(1000)

        assertTrue(result.sameAs(expectedBitmap))
    }

    @Test
    fun when_centerSquareCrop_is_called_on_a_image_bigger_height_we_crop_it_from_the_center() {
        val bitmap = assets(HEIGHT).use { BitmapFactory.decodeStream(it) }
        val expectedBitmap = assets(SQUARE).use { BitmapFactory.decodeStream(it) }

        val result = bitmap.centerSquareCrop(1000)

        assertTrue(result.sameAs(expectedBitmap))
    }

    companion object {
        internal const val SQUARE = "sample/square_test.png"
        internal const val WIDTH = "sample/width_test.png"
        internal const val HEIGHT = "sample/height_test.png"
    }
}
