@file:OptIn(ExperimentalFoundationApi::class)

package pt.andre.rijksmuseum.presentation.overview.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pt.andre.rijksmuseum.presentation.overview.model.CollectionViewItem

@Composable
internal fun OverviewItemList(
    modifier: Modifier,
    items: List<CollectionViewItem>
) {
    LazyColumn(modifier = modifier) {
        items.forEach { item ->
            when (item) {
                is CollectionViewItem.Item -> item {
                    Text(text = item.text)
                }
                is CollectionViewItem.Header -> stickyHeader {
                    Text(text = item.title)
                }
            }
        }
    }
}
