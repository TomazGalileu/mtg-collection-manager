package com.tomazgalileu.mtgcollectionmanager.ui.details

import android.content.Context
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomazgalileu.mtgcollectionmanager.data.local.AppDatabase
import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallRetrofitClient
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository
import com.tomazgalileu.mtgcollectionmanager.data.repository.ScryfallRepository

fun createCardDetailsViewModelFactory(
    context: Context
) = viewModelFactory {

    initializer {

        val repository = ScryfallRepository(
            api = ScryfallRetrofitClient.api
        )

        val database = AppDatabase.getDatabase(
            context.applicationContext
        )

        val collectionRepository = CollectionRepository(
            cardDao = database.cardDao()
        )

        CardDetailsViewModel(
            repository = repository,
            collectionRepository = collectionRepository
        )
    }
}
