package pt.andre.rijksmuseum.presentation.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pt.andre.rijksmuseum.presentation.R
import pt.andre.rijksmuseum.presentation.ui.Chip
import pt.andre.rijksmuseum.presentation.ui.RijksmuseumTheme

private const val IMAGE_HEIGHT_FRACTION = 0.35f

@Composable
internal fun DetailsItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    description: String?,
    author: String,
    year: String?
) {
    val topPadding = dimensionResource(id = R.dimen.details_view_top_padding)
    val horizontalPadding = dimensionResource(id = R.dimen.details_view_horizontal_padding)

    Column(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(IMAGE_HEIGHT_FRACTION),
            placeholder = ColorPainter(Color.Gray),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(
                top = topPadding,
                start = horizontalPadding,
                end = horizontalPadding
            ),
            text = title,
            fontSize = 32.sp
        )

        description?.let {
            Text(
                modifier = Modifier.padding(
                    top = topPadding,
                    start = horizontalPadding,
                    end = horizontalPadding
                ),
                text = description,
                fontSize = 18.sp
            )
        }

        LazyRow(
            modifier = Modifier.padding(
                top = topPadding
            )
        ) {
            item {
                Chip(
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.details_view_horizontal_padding),
                        end = dimensionResource(id = R.dimen.details_view_horizontal_padding)
                    ),
                    icon = Icons.Default.Person,
                    text = author
                )
            }

            item {
                Chip(
                    icon = Icons.Default.Info,
                    text = year ?: stringResource(id = R.string.unknown)
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsItemPreview() {
    RijksmuseumTheme {
        Surface {
            DetailsItem(
                imageUrl = "This is totally an valid url.",
                title = "Girl with a Pearl Earring",
                description = "Girl with a Pearl Earring is an oil painting by Dutch Golden Age " +
                    "painter Johannes Vermeer, dated c. 1665.",
                year = null,
                author = "Johannes Vermeer"
            )
        }
    }
}
