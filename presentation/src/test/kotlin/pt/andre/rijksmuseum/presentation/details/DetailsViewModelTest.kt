package pt.andre.rijksmuseum.presentation.details

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import pt.andre.rijksmuseum.domain.collection.model.CollectionDetailsItem
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.domain.utilities.asFailure
import pt.andre.rijksmuseum.domain.utilities.asSuccess
import pt.andre.rijksmuseum.presentation.details.mapper.toViewItem
import pt.andre.rijksmuseum.presentation.details.model.DetailsViewState
import kotlin.random.Random

private const val ID = "id"

internal class DetailsViewModelTest {

    private val repository = mockk<CollectionRepository>(relaxed = true)
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)

    @Before
    fun setup() {
        every { savedStateHandle.get<String>("id") } returns ID
    }

    @Test
    fun `when repository is loading we emit loading state`() = runTest {
        coEvery { repository.details(any()) } returns flowOf(Result.Loading)

        val vm = DetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        assertEquals(DetailsViewState.Loading, vm.state.value)
    }

    @Test
    fun `when repository returns failure we emit error state`() = runTest {
        coEvery { repository.details(any()) } returns flowOf(Exception().asFailure())

        val vm = DetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        assertEquals(DetailsViewState.Error, vm.state.value)
    }

    @Test
    fun `when repository returns success we emit success state`() = runTest {
        val item = CollectionDetailsItem(
            id = "${Random.nextInt()}",
            title = "${Random.nextInt()}",
            description = "${Random.nextInt()}",
            author = "${Random.nextInt()}",
            year = "${Random.nextInt()}",
            image = "${Random.nextInt()}"
        )

        coEvery { repository.details(any()) } returns flowOf(item.asSuccess())

        val vm = DetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,
            dispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        assertEquals(item.toViewItem(), vm.state.value)
    }
}
