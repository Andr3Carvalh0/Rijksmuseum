package pt.andre.rijksmuseum.presentation.overview.model

internal sealed class OverviewViewState {

    data class Success(
        val items: List<CollectionViewItem>,
        val isLoadingNextPage: Boolean
    ) : OverviewViewState()

    object Error : OverviewViewState()

    object Loading : OverviewViewState()
}
