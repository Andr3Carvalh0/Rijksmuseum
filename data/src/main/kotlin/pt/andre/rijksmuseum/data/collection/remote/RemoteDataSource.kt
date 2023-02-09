package pt.andre.rijksmuseum.data.collection.remote

import kotlinx.coroutines.CoroutineDispatcher
import pt.andre.rijksmuseum.data.mapper.mapResult
import pt.andre.rijksmuseum.domain.utilities.IODispatcher
import javax.inject.Inject

internal class RemoteDataSource @Inject constructor(
    private val service: CollectionService,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun items(offset: Int, itemsPerPage: Int) = mapResult(
        dispatcher = dispatcher,
        networkCall = {
            service.getCollection(
                offset = offset,
                itemsPerPage = itemsPerPage
            )
        }
    )

    suspend fun details(id: String) = mapResult(
        dispatcher = dispatcher,
        networkCall = { service.getCollectionItem(id) }
    )
}
