package com.tomazgalileu.mtgcollectionmanager.ui.collection

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository
import com.tomazgalileu.mtgcollectionmanager.data.repository.DeckRepository

fun createCollectionViewModelFactory(
    repository: CollectionRepository,
    deckRepository: DeckRepository
): ViewModelProvider.Factory {
    return viewModelFactory {
        initializer {
            CollectionViewModel(
                repository = repository,
                deckRepository = deckRepository
            )
        }
    }
}
