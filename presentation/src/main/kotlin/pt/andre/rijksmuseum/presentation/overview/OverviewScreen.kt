package pt.andre.rijksmuseum.presentation.overview

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun OverviewScreen(
    onItemClick: (id: String) -> Unit,
    vm: OverviewViewModel = viewModel()
) {
    Text(
        text = "Overview",
        modifier = Modifier.clickable {
            onItemClick("Ola")
        }
    )
}
