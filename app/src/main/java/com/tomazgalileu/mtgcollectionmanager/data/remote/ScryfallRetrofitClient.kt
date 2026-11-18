package com.tomazgalileu.mtgcollectionmanager.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ScryfallRetrofitClient {

    private const val BASE_URL = "https://api.scryfall.com/"

    val api: ScryfallApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScryfallApiService::class.java)
    }
}
