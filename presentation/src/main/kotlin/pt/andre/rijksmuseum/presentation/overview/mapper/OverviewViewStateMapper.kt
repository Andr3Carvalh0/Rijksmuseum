package pt.andre.rijksmuseum.presentation.overview.mapper

import pt.andre.rijksmuseum.domain.collection.model.CollectionItem
import pt.andre.rijksmuseum.presentation.overview.model.CollectionViewItem
import pt.andre.rijksmuseum.presentation.overview.model.OverviewViewState

internal fun List<CollectionItem>.toViewItems(
    previousItems: List<CollectionViewItem>?
): OverviewViewState.Success {
    val lastVisibleAuthor = (previousItems?.last() as? CollectionViewItem.Item)?.author

    val items = this
        .groupBy { it.author }
        .map { (author, metadata) ->
            val headerView = listOf(
                if (author == lastVisibleAuthor) null else CollectionViewItem.Header(title = author)
            )

            headerView + metadata.map {
                CollectionViewItem.Item(
                    id = it.id,
                    text = it.text,
                    author = it.author,
                    imageUrl = it.imageUrl
                )
            }
        }
        .flatten()
        .filterNotNull()

    return OverviewViewState.Success(
        items = if (previousItems != null) previousItems + items else items,
        isLoadingNextPage = false
    )
}
