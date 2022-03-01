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

@Module
@InstallIn(ViewModelComponent::class)
internal object TensorflowModule {
    @Provides
    fun provideObjectDetector(@ApplicationContext context: Context): ObjectDetector {
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(1)
            .setScoreThreshold(0.5f)
            .build()

        return ObjectDetector.createFromFileAndOptions(
            context,
            "apple_model.tflite",
            options
        )
    }

    @Provides
    fun provideTensorflow(detector: ObjectDetector): Tensorflow = TensorflowImpl(detector)
}
