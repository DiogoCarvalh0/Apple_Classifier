package pt.carvalho.apples.classifier.main

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import pt.carvalho.apples.classifier.di.IoDispatcher
import pt.carvalho.apples.classifier.di.MainTimeout
import pt.carvalho.apples.classifier.model.Apple
import pt.carvalho.apples.classifier.processing.ObjectDetector
import pt.carvalho.apples.classifier.utilities.TimeManager
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val detector: ObjectDetector,
    private val timeManager: TimeManager,
    @MainTimeout private val timeout: Long,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _result: MutableState<DisplayData> = mutableStateOf(DisplayData.Nothing)
    val result: State<DisplayData> = _result

    private var lastEmit: Long = -1L

    fun process(image: Bitmap) {
        // When we are showing a result dont process anything
        if (result.value is DisplayData.DetectedObject) return

        if (!timeManager.hasTimePassedSince(lastEmit, timeout) && lastEmit != -1L) return
        lastEmit = timeManager.now()

        viewModelScope.launch(ioDispatcher) {
            val result = runCatching { detector.detect(image) }.getOrNull()

            _result.value = if (result == null) {
                 DisplayData.Error()
            } else {
                DisplayData.DetectedObject(result)
            }
        }
    }

    fun onDismissed() {
        _result.value = DisplayData.Nothing
    }

    internal sealed class DisplayData {
        data class DetectedObject(
            val value: Apple
        ) : DisplayData()

        data class Error(
            val id: String = "${Random.nextInt()}"
        ) : DisplayData()

        object Nothing : DisplayData()
    }
}
