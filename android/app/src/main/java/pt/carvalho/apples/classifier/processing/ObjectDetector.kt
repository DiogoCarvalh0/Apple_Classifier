package pt.carvalho.apples.classifier.processing

import android.graphics.Bitmap
import pt.carvalho.apples.classifier.data.Database
import pt.carvalho.apples.classifier.data.Apple as AppleDTO
import pt.carvalho.apples.classifier.model.Apple as AppleVTO
import pt.carvalho.apples.classifier.processing.tensorflow.Tensorflow
import kotlin.math.roundToInt

private const val MAX_PERCENTAGE = 100

internal interface ObjectDetector {
    suspend fun detect(bitmap: Bitmap): AppleVTO?
}

internal class ObjectDetectorImpl(
    private val tensorflow: Tensorflow,
    private val database: Database
) : ObjectDetector {

    override suspend fun detect(bitmap: Bitmap): AppleVTO? {
        val label = tensorflow.classify(bitmap) ?: return null
        val percentage = (label.confidence * MAX_PERCENTAGE).roundToInt()

        return database.findLabel(label.name)?.toAppleVTO(
            percentage = percentage
        )
    }

    private fun AppleDTO.toAppleVTO(
        percentage: Int
    ): AppleVTO {
        return AppleVTO(
            name = name,
            description = description,
            picture = image,
            origin = origin.name,
            confidence = percentage
        )
    }
}
