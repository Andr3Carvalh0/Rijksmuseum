package pt.andre.rijksmuseum.domain.collection.model

data class CollectionDetailsItem(
    val id: String,
    val title: String,
    val description: String,
    val author: String,
    val year: String,
    val image: String
)
