package pt.carvalho.apples.classifier.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import pt.carvalho.apples.classifier.processing.tensorflow.Tensorflow
import pt.carvalho.apples.classifier.processing.tensorflow.TensorflowImpl

private const val MAX_RESULTS = 1
private const val SCORE_THRESHOLD = 0.15f
private const val MODEL_PATH = "apple_model.tflite"

@Module
@InstallIn(ViewModelComponent::class)
internal object TensorflowModule {
    @Provides
    fun provideObjectDetector(@ApplicationContext context: Context): ObjectDetector {
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(MAX_RESULTS)
            .setScoreThreshold(SCORE_THRESHOLD)
            .build()

        return ObjectDetector.createFromFileAndOptions(
            context,
            MODEL_PATH,
            options
        )
    }

    @Provides
    fun provideTensorflow(detector: ObjectDetector): Tensorflow = TensorflowImpl(detector)
}
