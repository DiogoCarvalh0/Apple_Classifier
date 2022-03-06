package pt.carvalho.apples.classifier.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import pt.carvalho.apples.classifier.utilities.AssetsReader
import pt.carvalho.apples.classifier.utilities.AssetsReaderImpl
import pt.carvalho.apples.classifier.utilities.TimeManager
import pt.carvalho.apples.classifier.utilities.TimeManagerImpl

@Module
@InstallIn(ViewModelComponent::class)
internal object UtilitiesModule {

    @Provides
    fun providesTimeManager(): TimeManager = TimeManagerImpl()

    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun providesAssetsReader(@ApplicationContext context: Context): AssetsReader = AssetsReaderImpl(context)
}