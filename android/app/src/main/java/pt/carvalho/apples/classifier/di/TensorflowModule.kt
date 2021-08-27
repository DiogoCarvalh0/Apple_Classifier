package pt.carvalho.apples.classifier.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pt.carvalho.apples.classifier.processing.tensorflow.Tensorflow
import pt.carvalho.apples.classifier.processing.tensorflow.TensorflowImpl

@Module
@InstallIn(ViewModelComponent::class)
internal object TensorflowModule {
    @Provides
    fun provideTensorflow(): Tensorflow = TensorflowImpl()
}
