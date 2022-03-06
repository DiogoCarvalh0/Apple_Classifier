package pt.carvalho.apples.classifier.processing

import android.graphics.Bitmap
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pt.carvalho.apples.classifier.data.Apple
import pt.carvalho.apples.classifier.data.Database
import pt.carvalho.apples.classifier.processing.tensorflow.ObjectDetected
import pt.carvalho.apples.classifier.processing.tensorflow.Tensorflow

internal class ObjectorDetectorTest {

    private val tensorflow = mock<Tensorflow>()
    private val database = mock<Database>()

    private val bitmap = mock<Bitmap>()

    private val detector = ObjectDetectorImpl(
        tensorflow = tensorflow,
        database = database
    )

    @Test
    fun `when tensorflow cant classify anything we return null`() = runBlocking {
        whenever(tensorflow.classify(bitmap)).thenReturn(null)

        val result = detector.detect(bitmap = bitmap)

        verify(database, never()).findLabel(any())

        assertNull(result)
    }

    @Test
    fun `when tensorflow can classify bitmap as fuji apple but we dont find it in the database we return null`() = runBlocking {
        whenever(tensorflow.classify(bitmap)).thenReturn(
            ObjectDetected(
                name = LABEL,
                confidence = 0.523468f
            )
        )

        whenever(database.findLabel(LABEL)).thenReturn(null)

        val result = detector.detect(bitmap = bitmap)

        verify(database, atLeastOnce()).findLabel(LABEL)
        assertNull(result)
    }

    @Test
    fun `when tensorflow can classify bitmap as fuji apple we return fuji information correctly`() = runBlocking {
        whenever(tensorflow.classify(bitmap)).thenReturn(
            ObjectDetected(
                name = LABEL,
                confidence = 0.523468f
            )
        )

        whenever(database.findLabel(LABEL)).thenReturn(
            Apple(
                id = LABEL,
                name = LABEL,
                description = DESCRIPTION,
                image = IMAGE,
                origin = Apple.Location(
                    name = ORIGIN
                )
            )
        )

        val result = detector.detect(bitmap = bitmap)

        assertNotNull(result)
        assertEquals(LABEL, result?.name)
        assertEquals(DESCRIPTION, result?.description)
        assertEquals(ORIGIN, result?.origin)
        assertEquals(IMAGE, result?.picture)
        assertEquals(PERCENTAGE, result?.confidence)
    }

    companion object {
        const val LABEL = "Fuji"
        const val DESCRIPTION = "This is a description"
        const val IMAGE = "https://www.google.com"
        const val ORIGIN = "Japan"
        const val PERCENTAGE = 52
    }
}
