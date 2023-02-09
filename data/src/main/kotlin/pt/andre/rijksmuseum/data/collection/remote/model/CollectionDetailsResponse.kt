package pt.andre.rijksmuseum.data.collection.remote.model

import androidx.annotation.Keep

@Keep
internal data class CollectionDetailsResponse(
    val artObject: ArtObjectResponse
)
