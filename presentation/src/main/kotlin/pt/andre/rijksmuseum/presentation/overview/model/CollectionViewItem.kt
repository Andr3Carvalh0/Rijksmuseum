package pt.andre.rijksmuseum.presentation.overview.model

internal sealed class CollectionViewItem {

    data class Item(
        val id: String,
        val text: String,
        val author: String,
        val imageUrl: String
    ) : CollectionViewItem()

    data class Header(
        val title: String
    ) : CollectionViewItem()
}
