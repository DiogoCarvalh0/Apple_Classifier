package pt.carvalho.apples.classifier.main

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.carvalho.apples.classifier.di.IoDispatcher
import pt.carvalho.apples.classifier.model.Apple
import pt.carvalho.apples.classifier.processing.tensorflow.Tensorflow
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val tensorflow: Tensorflow,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _result: MutableState<Apple?> = mutableStateOf(null)
    val result: State<Apple?> = _result

    private var isLocked: Boolean = false

    fun process(image: Bitmap) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                val result = tensorflow.classify(image)
                Log.v("diogo", result)
            }
        }
    }

    fun onDismissed() {
        isLocked = false
    }

    private fun emitValue(value: Apple?) {
        if (!isLocked) {
            _result.value = value
            isLocked = true
        }
    }
}
