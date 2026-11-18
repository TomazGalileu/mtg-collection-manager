package com.tomazgalileu.mtgcollectionmanager.ui.details

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallRetrofitClient
import com.tomazgalileu.mtgcollectionmanager.data.repository.ScryfallRepository

fun createCardDetailsViewModelFactory() = viewModelFactory {

    initializer {

        val repository = ScryfallRepository(
            api = ScryfallRetrofitClient.api
        )

        CardDetailsViewModel(
            repository = repository
        )
    }
}
