@file:OptIn(ExperimentalMaterial3Api::class)
package pt.andre.rijksmuseum.presentation.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import pt.andre.rijksmuseum.presentation.R
import pt.andre.rijksmuseum.presentation.overview.model.OverviewViewState
import pt.andre.rijksmuseum.presentation.overview.ui.OverviewItemList
import pt.andre.rijksmuseum.presentation.ui.Error
import pt.andre.rijksmuseum.presentation.ui.Loading

@Composable
internal fun OverviewScreen(
    onItemClick: (id: String) -> Unit,
    vm: OverviewViewModel = hiltViewModel()
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.surface
            )
        )

        when (val uiState = vm.state.value) {
            is OverviewViewState.Loading -> Loading(
                modifier = Modifier.fillMaxSize()
            )
            is OverviewViewState.Error -> Error(
                modifier = Modifier.fillMaxSize(),
                onRetryClick = vm::initialize
            )
            is OverviewViewState.Success -> OverviewItemList(
                modifier = Modifier.fillMaxSize(),
                items = uiState.items
            )
        }
    }
}
