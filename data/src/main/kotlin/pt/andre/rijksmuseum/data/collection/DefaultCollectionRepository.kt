package pt.andre.rijksmuseum.data.collection

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.andre.rijksmuseum.data.collection.remote.RemoteDataSource
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import pt.andre.rijksmuseum.domain.collection.model.CollectionDetailsItem
import pt.andre.rijksmuseum.domain.collection.model.CollectionItem
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.domain.utilities.mapSuccess
import javax.inject.Inject

internal class DefaultCollectionRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CollectionRepository {

    override fun items(offset: Int, itemsPerPage: Int): Flow<Result<List<CollectionItem>>> = flow {
        remoteDataSource.items(
            offset = offset,
            itemsPerPage = itemsPerPage
        ).mapSuccess { response ->
            response.artObjects.map {
                CollectionItem(
                    id = it.objectNumber,
                    text = it.title,
                    imageUrl = it.webImage?.url ?: ""
                )
            }
        }.run {
            emit(this)
        }
    }

    override fun details(id: String): Flow<Result<CollectionDetailsItem>> = flow {
        remoteDataSource.details(id)
            .mapSuccess { response ->
                CollectionDetailsItem(
                    id = response.objectNumber,
                    title = response.title,
                    description = response.description,
                    author = response.principalOrFirstMaker,
                    year = response.dating.presentingDate,
                    image = response.webImage?.url ?: ""
                )
            }.run {
                emit(this)
            }
    }
}
