package pt.carvalho.apples.classifier.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pt.carvalho.apples.classifier.navigation.Router
import pt.carvalho.apples.classifier.navigation.RouterImpl

@Module
@InstallIn(SingletonComponent::class)
internal object RouterModule {
    @Provides
    fun provideRouter(
        @ApplicationContext context: Context,
        @PackageName packageName: String
    ): Router = RouterImpl(context, packageName)
}
