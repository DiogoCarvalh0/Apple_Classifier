package pt.carvalho.apples.classifier.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

private const val THRESHOLD_MILLI = 5000L

@Module
@InstallIn(SingletonComponent::class)
internal object MainModule {
    @MainTimeout
    @Provides
    fun providesTimeout(): Long = THRESHOLD_MILLI
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class MainTimeout
