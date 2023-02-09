package pt.andre.rijksmuseum.presentation.details.mapper

import pt.andre.rijksmuseum.domain.collection.model.CollectionDetailsItem
import pt.andre.rijksmuseum.presentation.details.model.DetailsViewState

internal fun CollectionDetailsItem.toViewItem(): DetailsViewState.Success =
    DetailsViewState.Success(
        title = title,
        description = description,
        author = author,
        year = year,
        image = image
    )
