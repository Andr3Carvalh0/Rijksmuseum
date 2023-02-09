package pt.andre.rijksmuseum.presentation.details.model

internal sealed class DetailsViewState {
    data class Success(
        val title: String,
        val description: String?,
        val author: String,
        val year: String?,
        val image: String
    ) : DetailsViewState()

    object Error : DetailsViewState()

    object Loading : DetailsViewState()
}
