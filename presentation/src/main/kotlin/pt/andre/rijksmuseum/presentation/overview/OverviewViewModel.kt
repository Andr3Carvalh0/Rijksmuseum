package pt.andre.rijksmuseum.presentation.overview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import pt.andre.rijksmuseum.domain.utilities.IODispatcher
import pt.andre.rijksmuseum.presentation.overview.model.OverviewViewState
import pt.andre.rijksmuseum.presentation.overview.pagination.GetOverviewItemsUseCase
import javax.inject.Inject

@HiltViewModel
internal class OverviewViewModel @Inject constructor(
    private val getOverviewItemsUseCase: GetOverviewItemsUseCase,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val state: State<OverviewViewState>
        get() = _state

    private val _state: MutableState<OverviewViewState> =
        mutableStateOf(OverviewViewState.Loading)

    init { initialize() }

    fun initialize() {
        viewModelScope.launch(dispatcher) {
            getOverviewItemsUseCase.initialize()
                .collect { result -> _state.value = result }
        }
    }

    fun onItemVisible(index: Int) {
        viewModelScope.launch(dispatcher) {
            getOverviewItemsUseCase.onItemVisible(index)
        }
    }
}
