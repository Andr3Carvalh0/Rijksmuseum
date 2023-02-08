package pt.andre.rijksmuseum.data.collection.remote

import pt.andre.rijksmuseum.data.mapper.mapResult
import javax.inject.Inject

internal class RemoteDataSource @Inject constructor(
    private val service: CollectionService
) {

    suspend fun items(offset: Int, itemsPerPage: Int) = mapResult {
        service.getCollection(
            offset = offset,
            itemsPerPage = itemsPerPage
        )
    }

    suspend fun details(id: String) = mapResult {
        service.getCollectionItem(id)
    }
}
