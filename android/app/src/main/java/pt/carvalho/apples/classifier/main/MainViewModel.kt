package pt.carvalho.apples.classifier.main

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.carvalho.apples.classifier.di.IoDispatcher
import pt.carvalho.apples.classifier.processing.tensorflow.Tensorflow
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val tensorflow: Tensorflow,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    fun process(image: Bitmap) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                tensorflow.classify(image)
            }
        }
    }
}
