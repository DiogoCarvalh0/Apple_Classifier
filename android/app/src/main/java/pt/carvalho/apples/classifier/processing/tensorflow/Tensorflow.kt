package pt.carvalho.apples.classifier.processing.tensorflow

import android.graphics.Bitmap
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector

internal data class ObjectDetected(
    val name: String,
    val confidence: Float
)

internal interface Tensorflow {
    suspend fun classify(bitmap: Bitmap): ObjectDetected?
}

internal class TensorflowImpl(
    private val detector: ObjectDetector
) : Tensorflow {
    override suspend fun classify(bitmap: Bitmap): ObjectDetected? {
        val image = TensorImage.fromBitmap(bitmap)

        return detector.detect(image)
            .map { detection ->
                val obj = detection.categories.first()

                return ObjectDetected(
                    name = obj.label,
                    confidence = obj.score
                )
            }.firstOrNull()
    }
}
