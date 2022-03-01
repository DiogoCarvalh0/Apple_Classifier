package pt.carvalho.apples.classifier.processing.tensorflow

import android.graphics.Bitmap
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector

internal interface Tensorflow {
    suspend fun classify(bitmap: Bitmap): String
}

internal class TensorflowImpl(
    private val detector: ObjectDetector
) : Tensorflow {
    override suspend fun classify(bitmap: Bitmap): String {
        val image = TensorImage.fromBitmap(bitmap)

        val results = detector.detect(image)

        return results.map {
            // Get the top-1 category and craft the display text
            val category = it.categories.first()
            return category.label
        }.firstOrNull() ?: "None"
    }
}
