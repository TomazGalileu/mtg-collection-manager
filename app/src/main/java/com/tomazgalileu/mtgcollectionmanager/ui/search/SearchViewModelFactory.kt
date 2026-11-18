package com.tomazgalileu.mtgcollectionmanager.ui.search

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallRetrofitClient
import com.tomazgalileu.mtgcollectionmanager.data.repository.ScryfallRepository

fun createSearchViewModelFactory(): ViewModelProvider.Factory {
    return viewModelFactory {
        initializer {
            val repository = ScryfallRepository(
                api = ScryfallRetrofitClient.api
            )

            SearchViewModel(repository)
        }
    }
}
