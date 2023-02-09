@file:OptIn(ExperimentalFoundationApi::class)

package pt.andre.rijksmuseum.presentation.overview.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import pt.andre.rijksmuseum.presentation.R
import pt.andre.rijksmuseum.presentation.overview.model.CollectionViewItem
import pt.andre.rijksmuseum.presentation.ui.RijksmuseumTheme

@Composable
internal fun OverviewItemList(
    modifier: Modifier,
    listState: LazyListState = rememberLazyListState(),
    onItemClick: (id: String) -> Unit,
    items: List<CollectionViewItem>,
    isLoadingMore: Boolean
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = listState
        ) {
            items.forEach { item ->
                when (item) {
                    is CollectionViewItem.Item -> item {
                        OverviewListItem(
                            text = item.text,
                            imageUrl = item.imageUrl,
                            onItemClick = { onItemClick(item.id) }
                        )
                    }
                    is CollectionViewItem.Header -> stickyHeader {
                        OverviewHeader(text = item.title)
                    }
                }
            }
        }

        OverviewLoadingIndicator(isVisible = isLoadingMore)
    }
}

@Composable
internal fun OverviewHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            modifier = Modifier.padding(
                dimensionResource(id = R.dimen.header_text_padding)
            ),
            text = text,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
internal fun OverviewListItem(
    modifier: Modifier = Modifier,
    text: String,
    imageUrl: String,
    onItemClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        val isInEditMode = LocalView.current.isInEditMode

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isInEditMode) {
                Image(
                    modifier = Modifier.size(
                        dimensionResource(id = R.dimen.item_image_size)
                    ),
                    painter = ColorPainter(Color.Gray),
                    contentDescription = null
                )
            } else {
                AsyncImage(
                    model = imageUrl,
                    modifier = Modifier.size(
                        dimensionResource(id = R.dimen.item_image_size)
                    ),
                    placeholder = ColorPainter(Color.Gray),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.item_text_padding)
                ),
                text = text
            )
        }

        Divider()
    }
}

@Composable
internal fun OverviewLoadingIndicator(
    modifier: Modifier = Modifier,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.loading_more_progress_padding)
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.header_text_padding)
                ),
                text = stringResource(id = R.string.loading_more),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview
@Composable
private fun OverviewHeaderPreview() {
    RijksmuseumTheme {
        OverviewHeader(text = "Johannes Vermeer")
    }
}

@Preview
@Composable
private fun OverviewItemPreview() {
    RijksmuseumTheme {
        Surface {
            OverviewListItem(
                text = "Girl with a Pearl Earring",
                imageUrl = "This is totally an valid url.",
                onItemClick = { }
            )
        }
    }
}

@Preview
@Composable
private fun OverviewLoadingIndicatorPreview() {
    RijksmuseumTheme {
        OverviewLoadingIndicator(
            isVisible = true
        )
    }
}
