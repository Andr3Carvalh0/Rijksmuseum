package pt.andre.rijksmuseum.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import pt.andre.rijksmuseum.presentation.R

@Composable
internal fun Error(
    modifier: Modifier,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.error_screen_title),
            fontSize = dimensionResource(id = R.dimen.error_screen_title_size).value.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.error_screen_subtitle),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                dimensionResource(id = R.dimen.error_screen_subtitle_padding)
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        FloatingActionButton(
            onClick = onRetryClick
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    RijksmuseumTheme {
        Error(
            modifier = Modifier.fillMaxSize(),
            onRetryClick = { }
        )
    }
}
