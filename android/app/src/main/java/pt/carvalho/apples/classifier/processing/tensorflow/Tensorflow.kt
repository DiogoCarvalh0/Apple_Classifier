package pt.carvalho.apples.classifier.processing.tensorflow

import android.graphics.Bitmap

internal interface Tensorflow {
    suspend fun classify(image: Bitmap): String
}

internal class TensorflowImpl : Tensorflow {
    override suspend fun classify(image: Bitmap): String {
        return "apple"
    }
}
