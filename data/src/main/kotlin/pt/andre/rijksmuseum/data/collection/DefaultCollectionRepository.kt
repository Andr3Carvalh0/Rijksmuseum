package pt.andre.rijksmuseum.data.collection

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.andre.rijksmuseum.data.collection.mapper.toCollectionDetailsItem
import pt.andre.rijksmuseum.data.collection.mapper.toCollectionItem
import pt.andre.rijksmuseum.data.collection.remote.RemoteDataSource
import pt.andre.rijksmuseum.domain.collection.CollectionRepository
import pt.andre.rijksmuseum.domain.collection.model.CollectionDetailsItem
import pt.andre.rijksmuseum.domain.collection.model.CollectionItem
import pt.andre.rijksmuseum.domain.exceptions.EmptyResponseException
import pt.andre.rijksmuseum.domain.utilities.Result
import pt.andre.rijksmuseum.domain.utilities.asFailure
import pt.andre.rijksmuseum.domain.utilities.asSuccess
import pt.andre.rijksmuseum.domain.utilities.mapSuccess
import javax.inject.Inject

internal class DefaultCollectionRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CollectionRepository {

    override fun items(offset: Int, itemsPerPage: Int): Flow<Result<List<CollectionItem>>> = flow {
        remoteDataSource.items(
            offset = offset,
            itemsPerPage = itemsPerPage
        )
        .mapSuccess { response ->
            if (response.artObjects.isEmpty()) {
                EmptyResponseException().asFailure()
            } else {
                response.toCollectionItem()
                    .asSuccess()
            }
        }
        .run { emit(this) }
    }

    override fun details(id: String): Flow<Result<CollectionDetailsItem>> = flow {
        remoteDataSource.details(id)
            .mapSuccess { response ->
                Result.Success(response.toCollectionDetailsItem())
            }
            .run { emit(this) }
    }
}
