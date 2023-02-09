package pt.andre.rijksmuseum.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import pt.andre.rijksmuseum.presentation.R

@Composable
internal fun Chip(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String
) {
    val cornerSize = CornerSize(dimensionResource(id = R.dimen.chip_corner_radius))

    Row(
        modifier = modifier.wrapContentSize()
            .border(
                width = dimensionResource(id = R.dimen.chip_border_width),
                color = Color.Gray,
                shape = RoundedCornerShape(cornerSize, cornerSize, cornerSize, cornerSize)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.chip_icon_padding)
            ),
            imageVector = icon,
            contentDescription = text
        )

        Text(
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.chip_text_padding)
                ),
            text = text
        )
    }
}

@Preview
@Composable
private fun ChipPreview() {
    RijksmuseumTheme {
        Chip(
            icon = Icons.Default.Person,
            text = "Johannes Vermeer"
        )
    }
}
