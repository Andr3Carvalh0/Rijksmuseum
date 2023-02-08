package pt.andre.rijksmuseum.data.collection

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.andre.rijksmuseum.data.collection.remote.CollectionService
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CollectionModule {

    @Binds
    abstract fun bindCollectionRepository(default: DefaultCollectionRepository): CollectionRepository

    companion object {

        @Provides
        @Reusable
        internal fun provideCollectionService(retrofit: Retrofit): CollectionService =
            retrofit.create(CollectionService::class.java)
    }
}
