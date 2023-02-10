package pt.andre.rijksmuseum.presentation.overview.pagination

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import pt.andre.rijksmuseum.domain.pagination.PaginationManager
import pt.andre.rijksmuseum.domain.utilities.IODispatcher
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.presentation.overview.mapper.toViewItems
import pt.andre.rijksmuseum.presentation.overview.model.CollectionViewItem
import pt.andre.rijksmuseum.presentation.overview.model.OverviewViewState
import javax.inject.Inject

private const val INITIAL_OFFSET = 0

internal class GetOverviewItemsUseCase @Inject constructor(
    private val repository: CollectionRepository,
    paginationManager: PaginationManager,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : PaginationManager by paginationManager {

    private val state: MutableStateFlow<OverviewViewState> =
        MutableStateFlow(OverviewViewState.Loading)

    suspend fun initialize(): Flow<OverviewViewState> = state.also {
        fetchItems(INITIAL_OFFSET)
    }

    suspend fun onItemVisible(index: Int) {
        val previousItems = (state.value as? OverviewViewState.Success)?.items

        if (isProcessingRequest || previousItems == null) return

        val itemsWithoutHeaders = previousItems.filterIsInstance<CollectionViewItem.Item>()

        val normalizedIndex = previousItems.subList(0, index + 1)
            .filterIsInstance<CollectionViewItem.Item>()
            .lastIndex

        val hasReachRequestThreshold = shouldRequestNextPage(
            index = normalizedIndex,
            total = itemsWithoutHeaders.size
        )

        if (hasReachRequestThreshold) {
            fetchItems(
                offset = nextPageOffset(index = normalizedIndex)
            )
        }
    }

    private suspend fun fetchItems(offset: Int) {
        val previousItems = (state.value as? OverviewViewState.Success)?.items
        val isInitialRequest = previousItems == null

        setState(
            isInitialPage = isInitialRequest,
            initialValue = OverviewViewState.Loading,
            updatedValue = OverviewViewState.Success(
                items = previousItems ?: emptyList(),
                isLoadingNextPage = true
            )
        )

        isProcessingRequest = true

        withContext(dispatcher) {
            repository.items(
                offset = offset
            ).collect { result ->
                if (result.isSuccess() || result.isFailure()) {
                    isProcessingRequest = false
                }

                when (result) {
                    is Result.Success -> {
                        state.value = result.value.toViewItems(
                            previousItems = previousItems
                        )
                    }
                    else -> setState(
                        isInitialPage = isInitialRequest,
                        initialValue = if (result.isFailure()) {
                            OverviewViewState.Error
                        } else {
                            OverviewViewState.Loading
                        },
                        updatedValue = OverviewViewState.Success(
                            items = previousItems ?: emptyList(),
                            isLoadingNextPage = false
                        )
                    )
                }
            }
        }
    }

    private fun setState(
        isInitialPage: Boolean,
        initialValue: OverviewViewState,
        updatedValue: OverviewViewState?
    ) {
        if (isInitialPage) {
            state.value = initialValue
        } else {
            updatedValue?.let { state.value = it }
        }
    }
}
