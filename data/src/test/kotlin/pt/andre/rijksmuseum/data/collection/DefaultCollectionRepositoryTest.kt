package pt.andre.rijksmuseum.data.collection

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import pt.andre.rijksmuseum.data.collection.mapper.toCollectionDetailsItem
import pt.andre.rijksmuseum.data.collection.mapper.toCollectionItem
import pt.andre.rijksmuseum.data.collection.remote.RemoteDataSource
import pt.andre.rijksmuseum.data.collection.remote.model.ArtObjectResponse
import pt.andre.rijksmuseum.data.collection.remote.model.CollectionDetailsResponse
import pt.andre.rijksmuseum.data.collection.remote.model.CollectionListResponse
import pt.andre.rijksmuseum.data.collection.remote.model.WebImageResponse
import pt.andre.rijksmuseum.domain.exceptions.EmptyResponseException
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.domain.utilities.asFailure
import pt.andre.rijksmuseum.domain.utilities.asSuccess
import kotlin.random.Random

internal class DefaultCollectionRepositoryTest {

    private val remote: RemoteDataSource = mockk(relaxed = true)
    private val repository = DefaultCollectionRepository(remote)

    @Test
    fun `items returns failure when the network call fails`() = runTest {
        val expectedValue = Exception("whoopsie!").asFailure()

        coEvery { remote.items(any(), any()) } returns expectedValue

        repository.items(offset = Random.nextInt())
            .test {
                assertEquals(expectedValue, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `items maps success to domain DTO`() = runTest {
        val remoteResponse = CollectionListResponse(
            artObjects = listOf(
                ArtObjectResponse(
                    objectNumber = "${Random.nextInt()}",
                    title = "${Random.nextInt()}",
                    description = "${Random.nextInt()}",
                    dating = null,
                    principalOrFirstMaker = "${Random.nextInt()}",
                    webImage = WebImageResponse("${Random.nextInt()}")
                )
            )
        )

        val expectedValue = remoteResponse.toCollectionItem().asSuccess()

        coEvery { remote.items(any(), any()) } returns remoteResponse.asSuccess()

        repository.items(offset = Random.nextInt())
            .test {
                assertEquals(expectedValue, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `items maps to failure if success response has zero items`() = runTest {
        val remoteResponse = CollectionListResponse(
            artObjects = emptyList()
        )

        coEvery { remote.items(any(), any()) } returns remoteResponse.asSuccess()

        repository.items(offset = Random.nextInt())
            .test {
                val item = awaitItem()

                assertTrue(item is Result.Failure)
                assertTrue((item as? Result.Failure)?.exception is EmptyResponseException)

                awaitComplete()
            }
    }

    @Test
    fun `details returns failure when the network call fails`() = runTest {
        val expectedValue = Exception("whoopsie!").asFailure()

        coEvery { remote.details(any()) } returns expectedValue

        repository.details("${Random.nextInt()}")
            .test {
                assertEquals(expectedValue, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `details maps success to domain DTO`() = runTest {
        val remoteResponse = CollectionDetailsResponse(
            artObject = ArtObjectResponse(
                objectNumber = "${Random.nextInt()}",
                title = "${Random.nextInt()}",
                description = "${Random.nextInt()}",
                dating = ArtObjectResponse.DatingObjectResponse("${Random.nextInt()}"),
                principalOrFirstMaker = "${Random.nextInt()}",
                webImage = WebImageResponse("${Random.nextInt()}")
            )
        )

        val expectedValue = remoteResponse.toCollectionDetailsItem().asSuccess()

        coEvery { remote.details(any()) } returns remoteResponse.asSuccess()

        repository.details("${Random.nextInt()}")
            .test {
                assertEquals(expectedValue, awaitItem())
                awaitComplete()
            }
    }
}
