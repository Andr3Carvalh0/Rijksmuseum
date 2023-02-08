package pt.andre.rijksmuseum.presentation.overview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.presentation.overview.model.CollectionViewItem
import pt.andre.rijksmuseum.presentation.overview.model.OverviewViewState
import javax.inject.Inject

@HiltViewModel
internal class OverviewViewModel @Inject constructor(
    private val repository: CollectionRepository
) : ViewModel() {
    val state: State<OverviewViewState>
        get() = _state

    private val _state: MutableState<OverviewViewState> =
        mutableStateOf(OverviewViewState.Loading)

    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.items(
                0, 10
            ).collect { result ->
                _state.value = when (result) {
                    is Result.Success -> OverviewViewState.Success(
                        items = listOf(
                            CollectionViewItem.Header("Ola")
                        ) + result.value.map {
                            CollectionViewItem.Item(
                                id = it.id,
                                text = it.text,
                                imageUrl = it.imageUrl
                            )
                        },
                        isLoadingNextPage = false
                    )
                    is Result.Failure -> OverviewViewState.Error
                    is Result.Loading -> OverviewViewState.Loading
                }
            }
        }
    }
}
