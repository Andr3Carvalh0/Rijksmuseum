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
                    author = it.principalOrFirstMaker,
                    imageUrl = it.webImage.url
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
                    id = response.artObject.objectNumber,
                    title = response.artObject.title,
                    description = response.artObject.description,
                    author = response.artObject.principalOrFirstMaker,
                    year = response.artObject.dating?.presentingDate,
                    image = response.artObject.webImage.url
                )
            }.run {
                emit(this)
            }
    }
}
