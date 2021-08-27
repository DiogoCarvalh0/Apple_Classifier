package pt.carvalho.apples.classifier.main

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    fun process(image: Bitmap) {
        // Handle processing of tensorflow here
    }
}
