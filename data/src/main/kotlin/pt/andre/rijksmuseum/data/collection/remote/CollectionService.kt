package pt.andre.rijksmuseum.data.collection.remote

import pt.andre.rijksmuseum.data.collection.remote.model.CollectionDetailsResponse
import pt.andre.rijksmuseum.data.collection.remote.model.CollectionListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CollectionService {

    @GET("/api/nl/collection?s=artist&imgonly=true")
    suspend fun getCollection(
        @Query("p") offset: Int,
        @Query("ps") itemsPerPage: Int
    ): Response<CollectionListResponse>

    @GET("/api/nl/collection/{object-number}")
    suspend fun getCollectionItem(
        @Path("object-number") id: String
    ): Response<CollectionDetailsResponse>
}
