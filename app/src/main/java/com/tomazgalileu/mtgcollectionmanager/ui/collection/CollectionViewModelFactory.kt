package com.tomazgalileu.mtgcollectionmanager.ui.collection

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository

fun createCollectionViewModelFactory(
    repository: CollectionRepository
): ViewModelProvider.Factory {
    return viewModelFactory {
        initializer {
            CollectionViewModel(repository)
        }
    }
}
