package pt.andre.rijksmuseum.presentation.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import pt.andre.rijksmuseum.domain.utilities.IODispatcher
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.presentation.details.mapper.toViewItem
import pt.andre.rijksmuseum.presentation.details.model.DetailsViewState
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val repository: CollectionRepository,
    private val savedStateHandle: SavedStateHandle,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val state: State<DetailsViewState>
        get() = _state

    private val _state: MutableState<DetailsViewState> =
        mutableStateOf(DetailsViewState.Loading)

    private val itemId: String
        get() = savedStateHandle.get<String>("id")
            ?: throw IllegalArgumentException("Missing itemId!")

    init {
        initialize()
    }

    fun initialize() {
        _state.value = DetailsViewState.Loading

        viewModelScope.launch(dispatcher) {
            repository.details(itemId)
                .collect { result ->
                    _state.value = when (result) {
                        is Result.Success -> result.value.toViewItem()
                        is Result.Failure -> DetailsViewState.Error
                        is Result.Loading -> DetailsViewState.Loading
                    }
                }
        }
    }
}
