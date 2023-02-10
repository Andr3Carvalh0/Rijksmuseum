package pt.andre.rijksmuseum.data.collection.mapper

import pt.andre.rijksmuseum.data.collection.remote.model.CollectionDetailsResponse
import pt.andre.rijksmuseum.data.collection.remote.model.CollectionListResponse
import pt.andre.rijksmuseum.domain.collection.model.CollectionDetailsItem
import pt.andre.rijksmuseum.domain.collection.model.CollectionItem

internal fun CollectionListResponse.toCollectionItem() =
    artObjects.map {
        CollectionItem(
            id = it.objectNumber,
            text = it.title,
            author = it.principalOrFirstMaker,
            imageUrl = it.webImage.url
        )
    }

internal fun CollectionDetailsResponse.toCollectionDetailsItem() =
    CollectionDetailsItem(
        id = artObject.objectNumber,
        title = artObject.title,
        description = artObject.description,
        author = artObject.principalOrFirstMaker,
        year = artObject.dating?.presentingDate,
        image = artObject.webImage.url
    )
