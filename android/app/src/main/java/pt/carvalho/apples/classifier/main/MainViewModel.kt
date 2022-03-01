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
import pt.carvalho.apples.classifier.model.Apple
import pt.carvalho.apples.classifier.model.SAMPLE
import pt.carvalho.apples.classifier.processing.tensorflow.Tensorflow
import javax.inject.Inject
import kotlin.random.Random

private const val THRESHOLD_MILLI = 5000

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val tensorflow: Tensorflow,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _result: MutableState<DisplayData> = mutableStateOf(DisplayData.Nothing)
    val result: State<DisplayData> = _result

    private var lastEmit: Long = -1L

    fun process(image: Bitmap) {
        // When we are showing a result dont process anything
        if (result.value is DisplayData.DetectedObject) return

        val currentTime = System.currentTimeMillis()
        if ((currentTime - THRESHOLD_MILLI <= lastEmit) && lastEmit != -1L) return

        lastEmit = currentTime

        viewModelScope.launch(ioDispatcher) {
            val processedResult = runCatching { tensorflow.classify(image) }.getOrNull()

            _result.value = if (processedResult == null) {
                 DisplayData.Error()
            } else {
                DisplayData.DetectedObject(
                    Apple(
                        name = processedResult,
                        description = " ¯\\_(ツ)_/¯",
                        picture = SAMPLE.picture
                    )
                )
            }
        }
    }

    fun onDismissed() {
        _result.value = DisplayData.Nothing
    }

    internal sealed class DisplayData {
        data class DetectedObject(
            val value: Apple
        ): DisplayData()

        data class Error(
            val id: String = "${Random.nextInt()}"
        ): DisplayData()

        object Nothing: DisplayData()
    }
}
