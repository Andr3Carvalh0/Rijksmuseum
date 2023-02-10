package pt.andre.rijksmuseum.data.collection.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import pt.andre.rijksmuseum.data.collection.remote.model.ArtObjectResponse
import pt.andre.rijksmuseum.data.collection.remote.model.CollectionDetailsResponse
import pt.andre.rijksmuseum.data.collection.remote.model.CollectionListResponse
import pt.andre.rijksmuseum.data.collection.remote.model.WebImageResponse
import pt.andre.rijksmuseum.data.utilities.errorResponse
import pt.andre.rijksmuseum.data.utilities.successResponseFromFile
import pt.andre.rijksmuseum.domain.exceptions.AuthenticationException
import pt.andre.rijksmuseum.domain.exceptions.ServerException
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.domain.utilities.asSuccess
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random

internal class RemoteDataSourceTest {

    private lateinit var server: MockWebServer
    private lateinit var service: CollectionService

    @Test
    fun `items call parses response correctly`() = runTest {
        val remote = RemoteDataSource(
            service = service,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        server.enqueue(
            successResponseFromFile("overview_items.json")
        )

        val expected = CollectionListResponse(
            artObjects = listOf(
                ArtObjectResponse(
                    objectNumber = "NG-MC-1023",
                    title = "Model van een waterruiter",
                    description = null,
                    dating = null,
                    principalOrFirstMaker = "'s Lands Werf Amsterdam",
                    webImage = WebImageResponse(
                        url = "https://lh4.ggpht.com/1Dr0Q0HJd_8COtSKmQ0JiW4rA" +
                                "X77YR8jsP0JeRxorwu3oumCUTYMsxBSPqfgaRWjlO9uJt3Uf" +
                                "i1Vfz5yY1nGrHBwaGA=s0"
                    )
                )
            )
        ).asSuccess()

        val result = remote.items(
            offset = Random.nextInt(),
            itemsPerPage = Random.nextInt()
        )

        assertEquals(expected, result)
    }

    @Test
    fun `items returns AuthenticationException() when server returns 401`() = runTest {
        val remote = RemoteDataSource(
            service = service,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        server.enqueue(errorResponse(401))

        val result = remote.items(
            offset = Random.nextInt(),
            itemsPerPage = Random.nextInt()
        )

        assertTrue(result is Result.Failure)
        assertTrue((result as? Result.Failure)?.exception is AuthenticationException)
    }

    @Test
    fun `items returns ServerException when server returns 500`() = runTest {
        val remote = RemoteDataSource(
            service = service,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        server.enqueue(errorResponse(500))

        val result = remote.items(
            offset = Random.nextInt(),
            itemsPerPage = Random.nextInt()
        )

        assertTrue(result is Result.Failure)
        assertTrue((result as? Result.Failure)?.exception is ServerException)
    }

    @Test
    fun `items details call parses response correctly`() = runTest {
        val remote = RemoteDataSource(
            service = service,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        server.enqueue(
            successResponseFromFile("item_details.json")
        )

        val expected = CollectionDetailsResponse(
            artObject = ArtObjectResponse(
                objectNumber = "NG-MC-1023",
                title = "Model van een waterruiter",
                description = "Model van een waterpalissade of waterruiter. Het is een " +
                        "dakvormig raamwerk met twee rijen scherpe punten bovenaan, in " +
                        "één richting wijzend. Aan de ene kant heeft het gewone ankers, " +
                        "aan de ander dreggen.",
                dating = ArtObjectResponse.DatingObjectResponse("1800"),
                principalOrFirstMaker = "'s Lands Werf Amsterdam",
                webImage = WebImageResponse(
                    url = "https://lh4.ggpht.com/1Dr0Q0HJd_8COtSKmQ0JiW4rA" +
                            "X77YR8jsP0JeRxorwu3oumCUTYMsxBSPqfgaRWjlO9uJt3Uf" +
                            "i1Vfz5yY1nGrHBwaGA=s0"
                )
            )
        ).asSuccess()

        val result = remote.details(id = "${Random.nextInt()}")

        assertEquals(expected, result)
    }


    @Test
    fun `details returns AuthenticationException() when server returns 401`() = runTest {
        val remote = RemoteDataSource(
            service = service,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        server.enqueue(errorResponse(401))

        val result = remote.details(
            id = "${Random.nextInt()}"
        )

        assertTrue(result is Result.Failure)
        assertTrue((result as? Result.Failure)?.exception is AuthenticationException)
    }

    @Test
    fun `details returns ServerException when server returns 500`() = runTest {
        val remote = RemoteDataSource(
            service = service,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        server.enqueue(errorResponse(500))

        val result = remote.details(
            id = "${Random.nextInt()}"
        )

        assertTrue(result is Result.Failure)
        assertTrue((result as? Result.Failure)?.exception is ServerException)
    }


    @Before
    fun setup() {
        server = MockWebServer()

        val httpClient = OkHttpClient.Builder().build()

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
