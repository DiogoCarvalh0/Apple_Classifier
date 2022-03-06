package pt.carvalho.apples.classifier.main

import android.graphics.Bitmap
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import pt.carvalho.apples.classifier.model.SAMPLE
import pt.carvalho.apples.classifier.processing.ObjectDetector
import pt.carvalho.apples.classifier.utilities.TimeManager

internal class MainViewModelTest {

    private val objectDetector = mock<ObjectDetector>()
    private val timeManager = mock<TimeManager>()

    private val bitmap = mock<Bitmap>()

    private val viewModel = MainViewModel(
        detector = objectDetector,
        ioDispatcher = TestCoroutineDispatcher(),
        timeout = 0L,
        timeManager = timeManager
    )

    @Before
    fun setup() {
        whenever(timeManager.now()).thenReturn(1L)
        whenever(timeManager.hasTimePassedSince(any(), any())).thenReturn(true)
    }

    @After
    fun cleanup() {
        viewModel.onDismissed()
    }

    @Test
    fun `when we detect an apple we emit DetectedObject object`() = runBlocking {
        whenever(objectDetector.detect(bitmap)).thenReturn(SAMPLE)

        viewModel.process(bitmap)

        val result = viewModel.result.value
        assertTrue(result is MainViewModel.DisplayData.DetectedObject)
        assertEquals(SAMPLE, (result as MainViewModel.DisplayData.DetectedObject).value)
    }

    @Test
    fun `when we detect an apple we emit DetectedObject object and after that we dont process any more data`() = runBlocking {
        val anotherBitmap = mock<Bitmap>()
        whenever(objectDetector.detect(bitmap)).thenReturn(SAMPLE)
        whenever(objectDetector.detect(anotherBitmap)).thenReturn(null)

        viewModel.process(bitmap)

        var result = viewModel.result.value
        assertTrue(result is MainViewModel.DisplayData.DetectedObject)
        assertEquals(SAMPLE, (result as MainViewModel.DisplayData.DetectedObject).value)

        viewModel.process(anotherBitmap)

        result = viewModel.result.value
        assertTrue(result is MainViewModel.DisplayData.DetectedObject)
        assertEquals(SAMPLE, (result as MainViewModel.DisplayData.DetectedObject).value)
    }

    @Test
    fun `after dismissing the current result we do process`() = runBlocking {
        whenever(objectDetector.detect(bitmap)).thenReturn(SAMPLE)

        // Validate object
        viewModel.process(bitmap)

        var result = viewModel.result.value
        assertTrue(result is MainViewModel.DisplayData.DetectedObject)
        assertEquals(SAMPLE, (result as MainViewModel.DisplayData.DetectedObject).value)

        // When dismissed
        viewModel.onDismissed()
        result = viewModel.result.value
        assertTrue(result is MainViewModel.DisplayData.Nothing)

        // Validate object again
        viewModel.process(bitmap)
        result = viewModel.result.value

        assertTrue(result is MainViewModel.DisplayData.DetectedObject)
        assertEquals(SAMPLE, (result as MainViewModel.DisplayData.DetectedObject).value)
    }

    @Test
    fun `if we are in a timeout we dont process any data`() = runBlocking {
        whenever(objectDetector.detect(bitmap)).thenReturn(SAMPLE)

        // Validate object
        viewModel.process(bitmap)

        var result = viewModel.result.value
        assertTrue(result is MainViewModel.DisplayData.DetectedObject)
        assertEquals(SAMPLE, (result as MainViewModel.DisplayData.DetectedObject).value)

        // When dismissed
        viewModel.onDismissed()
        result = viewModel.result.value
        assertTrue(result is MainViewModel.DisplayData.Nothing)

        // Validate object again and it should remain the same (Nothing)
        whenever(timeManager.hasTimePassedSince(any(), any())).thenReturn(false)

        viewModel.process(bitmap)
        result = viewModel.result.value

        assertTrue(result is MainViewModel.DisplayData.Nothing)
    }
}