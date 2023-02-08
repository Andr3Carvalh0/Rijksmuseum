package pt.andre.rijksmuseum.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.andre.rijksmuseum.data.BuildConfig
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKey

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @ApiKey
    fun provideApiKey(): String = BuildConfig.RIJKSMUSEUM_API_KEY

    @Provides
    @ApiUrl
    fun provideApiURL(): String = BuildConfig.RIJKSMUSEUM_URL
}
