package pt.andre.rijksmuseum.data.collection.remote.model

import androidx.annotation.Keep

@Keep
internal data class CollectionListResponse(
    val artObjects: List<ArtObjectResponse>,
)

@Keep
internal data class ArtObjectResponse(
    val objectNumber: String,
    val title: String,
    val principalOrFirstMaker: String,
    val webImage: WebImageResponse?
)
