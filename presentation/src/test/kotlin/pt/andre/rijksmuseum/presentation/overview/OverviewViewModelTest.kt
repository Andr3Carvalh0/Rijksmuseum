package pt.andre.rijksmuseum.presentation.overview

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import pt.andre.rijksmuseum.domain.collection.model.CollectionItem
import pt.andre.rijksmuseum.presentation.overview.mapper.toViewItems
import pt.andre.rijksmuseum.presentation.overview.model.OverviewViewState
import pt.andre.rijksmuseum.presentation.overview.pagination.GetOverviewItemsUseCase
import kotlin.random.Random

internal class OverviewViewModelTest {

    private val overviewItemsUseCase = mockk<GetOverviewItemsUseCase>(relaxed = true)

    @Test
    fun `when usecase is loading we emit loading state`() = runTest {
        coEvery { overviewItemsUseCase.initialize() } returns flowOf(OverviewViewState.Loading)

        val vm = OverviewViewModel(
            getOverviewItemsUseCase = overviewItemsUseCase,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        assertEquals(OverviewViewState.Loading, vm.state.value)
    }

    @Test
    fun `when usecase returns failure we emit error state`() = runTest {
        coEvery { overviewItemsUseCase.initialize() } returns flowOf(OverviewViewState.Error)

        val vm = OverviewViewModel(
            getOverviewItemsUseCase = overviewItemsUseCase,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        assertEquals(OverviewViewState.Error, vm.state.value)
    }

    @Test
    fun `when usecase returns success we emit success state`() = runTest {
        val expected = listOf(item(), item()).toViewItems(null)

        coEvery { overviewItemsUseCase.initialize() } returns flowOf(expected)

        val vm = OverviewViewModel(
            getOverviewItemsUseCase = overviewItemsUseCase,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        assertEquals(expected, vm.state.value)
    }

    @Test
    fun `onItemVisible calls onItemVisible from the usecase`() = runTest {
        coEvery { overviewItemsUseCase.initialize() } returns flowOf(OverviewViewState.Loading)

        val vm = OverviewViewModel(
            getOverviewItemsUseCase = overviewItemsUseCase,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        val index = Random.nextInt()

        vm.onItemVisible(index)

        coVerify { overviewItemsUseCase.onItemVisible(index) }
    }
}

internal fun item(): CollectionItem = CollectionItem(
    id = "${Random.nextInt()}",
    text = "${Random.nextInt()}",
    author = "${Random.nextInt()}",
    imageUrl = "${Random.nextInt()}"
)
