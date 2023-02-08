package pt.andre.rijksmuseum.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: String
        get() = savedStateHandle.get<String>("id")
            ?: throw IllegalArgumentException("Missing itemId!")
}
