package pt.carvalho.apples.classifier.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
internal object PackageManagerModule {
    @Provides
    @PackageName
    fun providePackageName(@ApplicationContext context: Context): String {
        return context.packageName
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class PackageName
