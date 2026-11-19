package com.tomazgalileu.mtgcollectionmanager.ui.decks

import android.content.Context
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomazgalileu.mtgcollectionmanager.data.local.AppDatabase
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository
import com.tomazgalileu.mtgcollectionmanager.data.repository.DeckRepository

fun createDecksViewModelFactory(
    context: Context
) = viewModelFactory {

    initializer {

        val database = AppDatabase.getDatabase(
            context.applicationContext
        )

        val collectionRepository = CollectionRepository(
            cardDao = database.cardDao()
        )

        val deckRepository = DeckRepository(
            context = context.applicationContext,
            collectionRepository = collectionRepository
        )

        DecksViewModel(
            deckRepository = deckRepository,
            collectionRepository = collectionRepository
        )
    }
}
