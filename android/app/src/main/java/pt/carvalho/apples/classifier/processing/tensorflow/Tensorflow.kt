package pt.carvalho.apples.classifier.processing.tensorflow

import android.graphics.Bitmap
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector

internal interface Tensorflow {
    suspend fun classify(bitmap: Bitmap): String?
}

internal class TensorflowImpl(
    private val detector: ObjectDetector
) : Tensorflow {
    override suspend fun classify(bitmap: Bitmap): String? {
        val image = TensorImage.fromBitmap(bitmap)

        return detector.detect(image)
            .map { detection ->
                detection.categories.first().label
            }.firstOrNull()
    }
}
