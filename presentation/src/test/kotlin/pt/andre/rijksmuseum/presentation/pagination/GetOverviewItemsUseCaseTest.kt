package pt.andre.rijksmuseum.presentation.pagination

import app.cash.turbine.test
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import pt.andre.rijksmuseum.domain.pagination.ITEMS_PER_PAGE
import pt.andre.rijksmuseum.domain.pagination.PaginationManager
import pt.andre.rijksmuseum.domain.utilities.asFailure
import pt.andre.rijksmuseum.domain.utilities.asSuccess
import pt.andre.rijksmuseum.presentation.overview.item
import pt.andre.rijksmuseum.presentation.overview.mapper.toViewItems
import pt.andre.rijksmuseum.presentation.overview.model.CollectionViewItem
import pt.andre.rijksmuseum.presentation.overview.model.OverviewViewState
import pt.andre.rijksmuseum.presentation.overview.pagination.GetOverviewItemsUseCase
import kotlin.random.Random

internal class GetOverviewItemsUseCaseTest {

    private val repository = mockk<CollectionRepository>(relaxed = true)
    private val paginationManager = mockk<PaginationManager>(relaxed = true)

    @Test
    fun `initialize emits loading state`() = runTest {
        usecase().initialize().test {
            assertEquals(OverviewViewState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initialize emits error state if repository fails`() = runTest {
        coEvery { repository.items(any(), any()) } returns flowOf(Exception().asFailure())

        usecase().initialize().test {
            assertEquals(OverviewViewState.Error, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initialize emits items with header on success`() = runTest {
        val items = listOf(item(), item())
        val expected = items.toViewItems(null)

        coEvery { repository.items(any(), any()) } returns flowOf(items.asSuccess())

        usecase().initialize().test {
            assertEquals(expected, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `items with the same author are grouped under the same header`() = runTest {
        val item = item()
        val items = listOf(item, item)
        val expected = items.toViewItems(null)

        coEvery { repository.items(any(), any()) } returns flowOf(items.asSuccess())

        usecase().initialize().test {
            val result = (awaitItem() as OverviewViewState.Success)

            assertEquals(expected, result)
            assertEquals(3, result.items.size)
            assertTrue(result.items.first() is CollectionViewItem.Header)
            assertTrue(result.items[1] is CollectionViewItem.Item)
            assertTrue(result.items.last() is CollectionViewItem.Item)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when we dont have the first page onItemVisible doesnt trigger a request`() = runTest {
        usecase().onItemVisible(Random.nextInt())

        coVerify {
            repository.items(any(), any()) wasNot called
        }
    }

    @Test
    fun `when we are processing request dont trigger a new request`() = runTest {
        usecase().onItemVisible(Random.nextInt())
        every { paginationManager.isProcessingRequest } returns true

        coVerify {
            repository.items(any(), any()) wasNot called
        }
    }

    @Test
    fun `requesting the next page after initial value updates correctly on success`() = runTest {
        val item = item()
        val items = listOf(item, item)

        coEvery { repository.items(any(), any()) } returns flowOf(items.asSuccess())
        coEvery { paginationManager.isProcessingRequest } returns false
        coEvery { paginationManager.shouldRequestNextPage(any(), any()) } returns true
        coEvery { paginationManager.nextPageOffset(any(), any()) } returns items.size

        val usecase = usecase()

        usecase.initialize().test {
            val initialItems = items.toViewItems(null)

            assertEquals(initialItems, awaitItem())

            usecase.onItemVisible(0)

            assertEquals(initialItems.copy(isLoadingNextPage = true), awaitItem())
            assertEquals(items.toViewItems(initialItems.items), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `requesting the next page after initial value updates doesnt emit failure`() = runTest {
        val item = item()
        val items = listOf(item, item)

        coEvery { repository.items(0, ITEMS_PER_PAGE) } returns flowOf(items.asSuccess())
        coEvery { repository.items(items.size, ITEMS_PER_PAGE) } returns flowOf(Exception().asFailure())
        coEvery { paginationManager.isProcessingRequest } returns false
        coEvery { paginationManager.shouldRequestNextPage(any(), any()) } returns true
        coEvery { paginationManager.nextPageOffset(any(), any()) } returns items.size

        val usecase = usecase()

        usecase.initialize().test {
            val initialItems = items.toViewItems(null)

            assertEquals(initialItems, awaitItem())

            usecase.onItemVisible(0)

            assertEquals(initialItems.copy(isLoadingNextPage = true), awaitItem())
            assertEquals(initialItems, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun TestScope.usecase() = GetOverviewItemsUseCase(
        repository = repository,
        paginationManager = paginationManager,
        dispatcher = UnconfinedTestDispatcher(testScheduler)
    )
}
