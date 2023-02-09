package pt.andre.rijksmuseum.data.utilities

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import pt.andre.rijksmuseum.domain.utilities.IODispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    @IODispatcher
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
