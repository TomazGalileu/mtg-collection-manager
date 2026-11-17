package com.tomazgalileu.mtgcollectionmanager.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ScryfallApiService {

    @Headers(
        "Accept: application/json",
        "User-Agent: MTGCollectionManager/1.0"
    )
    @GET("cards/search")
    suspend fun searchCards(
        @Query("q") query: String
    ): ScryfallSearchResponse
}
