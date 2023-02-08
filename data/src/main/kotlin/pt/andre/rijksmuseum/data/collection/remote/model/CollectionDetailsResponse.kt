package pt.andre.rijksmuseum.data.collection.remote.model

import androidx.annotation.Keep

@Keep
internal data class CollectionDetailsResponse(
    val objectNumber: String,
    val title: String,
    val description: String,
    val dating: DatingObjectResponse,
    val principalOrFirstMaker: String,
    val webImage: WebImageResponse?
) {

    data class DatingObjectResponse(
        val presentingDate: String
    )
}
