package pt.carvalho.apples.classifier.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import pt.carvalho.apples.classifier.data.Apple
import pt.carvalho.apples.classifier.data.Database
import pt.carvalho.apples.classifier.data.DatabaseImpl
import pt.carvalho.apples.classifier.utilities.AssetsReader

@Module
@InstallIn(ViewModelComponent::class)
internal object DatabaseModule {

    @Provides
    fun provideDatabase(moshi: Moshi, assetsReader: AssetsReader): Database {
        val type = Types.newParameterizedType(MutableList::class.java, Apple::class.java)
        val adapter: JsonAdapter<List<Apple>> = moshi.adapter(type)

        return DatabaseImpl(
            adapter = adapter,
            assetsReader = assetsReader
        )
    }
}
