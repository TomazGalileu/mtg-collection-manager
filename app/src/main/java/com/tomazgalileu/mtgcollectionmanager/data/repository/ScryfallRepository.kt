package com.tomazgalileu.mtgcollectionmanager.data.repository

import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallApiService
import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallSearchResponse

class ScryfallRepository(
    private val api: ScryfallApiService
) {

    suspend fun searchCards(
        query: String
    ): ScryfallSearchResponse {

        val searchText = query.trim()

        require(searchText.isNotEmpty()) {
            "O nome da carta não pode estar vazio."
        }

        return api.searchCards(searchText)
    }
}
