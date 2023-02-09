package pt.andre.rijksmuseum.presentation.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import pt.andre.rijksmuseum.presentation.details.model.DetailsViewState
import pt.andre.rijksmuseum.presentation.details.ui.DetailsItem
import pt.andre.rijksmuseum.presentation.ui.Error
import pt.andre.rijksmuseum.presentation.ui.Loading

@Composable
internal fun DetailsScreen(
    vm: DetailsViewModel = hiltViewModel()
) {
    when (val uiState = vm.state.value) {
        is DetailsViewState.Loading -> Loading(
            modifier = Modifier.fillMaxSize()
        )
        is DetailsViewState.Error -> Error(
            modifier = Modifier.fillMaxSize(),
            onRetryClick = vm::initialize
        )
        is DetailsViewState.Success -> DetailsItem(
            imageUrl = uiState.image,
            title = uiState.title,
            description = uiState.description,
            author = uiState.author,
            year = uiState.year
        )
    }
}
