package pt.andre.rijksmuseum.domain.collection

import kotlinx.coroutines.flow.Flow
import pt.andre.rijksmuseum.domain.collection.model.CollectionDetailsItem
import pt.andre.rijksmuseum.domain.collection.model.CollectionItem
import pt.andre.rijksmuseum.domain.utilities.Result

interface CollectionRepository {

    fun items(offset: Int, itemsPerPage: Int): Flow<Result<List<CollectionItem>>>

    fun details(id: String): Flow<Result<CollectionDetailsItem>>
}
