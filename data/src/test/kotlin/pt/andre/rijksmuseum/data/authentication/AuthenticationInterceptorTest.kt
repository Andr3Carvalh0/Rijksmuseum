package pt.andre.rijksmuseum.data.authentication

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import pt.andre.rijksmuseum.data.collection.remote.CollectionService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val DUMB_KEY = "dumb_key"
private const val COLLECTION_ID = "1"

internal class AuthenticationInterceptorTest {

    private lateinit var server: MockWebServer
    private lateinit var service: CollectionService

    @Test
    fun `api key is added to colletion details request`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))

        service.getCollectionItem(COLLECTION_ID)

        val request = server.takeRequest().path

        assertTrue(
            request?.contains("$AUTHENTICATION_PARAM=$DUMB_KEY") == true
        )
    }

    @Test
    fun `api key is added to colletions request`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404))

        service.getCollection(
            offset = 0,
            itemsPerPage = 10
        )

        val request = server.takeRequest().path

        assertTrue(
            request?.contains("$AUTHENTICATION_PARAM=$DUMB_KEY") == true
        )
    }

    @Before
    fun setup() {
        server = MockWebServer()

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(DUMB_KEY))
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()

        service = retrofit.create(CollectionService::class.java)
    }

    @After
    fun cleanup() = server.close()
}
