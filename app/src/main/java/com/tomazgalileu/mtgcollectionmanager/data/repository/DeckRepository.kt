package com.tomazgalileu.mtgcollectionmanager.data.repository

import android.content.Context
import com.tomazgalileu.mtgcollectionmanager.data.model.Deck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class DeckRepository(
    context: Context,
    private val collectionRepository: CollectionRepository
) {

    private val preferences =
        context.applicationContext.getSharedPreferences(
            "mtg_decks",
            Context.MODE_PRIVATE
        )

    private val mutex = Mutex()

    private val _decks = MutableStateFlow(loadDecks())

    val decks = _decks.asStateFlow()

    private fun loadDecks(): List<Deck> {

        val json = preferences.getString(
            "decks",
            "[]"
        ) ?: "[]"

        val jsonArray = JSONArray(json)

        val result = mutableListOf<Deck>()

        for (i in 0 until jsonArray.length()) {

            val jsonDeck = jsonArray.getJSONObject(i)

            val jsonCards = jsonDeck.getJSONArray("cardIds")

            val cardIds = mutableListOf<String>()

            for (j in 0 until jsonCards.length()) {
                cardIds.add(jsonCards.getString(j))
            }

            result.add(
                Deck(
                    id = jsonDeck.getLong("id"),
                    name = jsonDeck.getString("name"),
                    cardIds = cardIds
                )
            )
        }

        return result
    }

    private fun saveDecks(
        updatedDecks: List<Deck>
    ): Boolean {

        val jsonArray = JSONArray()

        updatedDecks.forEach { deck ->

            val jsonDeck = JSONObject()

            jsonDeck.put("id", deck.id)
            jsonDeck.put("name", deck.name)

            val jsonCards = JSONArray()

            deck.cardIds.forEach { cardId ->
                jsonCards.put(cardId)
            }

            jsonDeck.put("cardIds", jsonCards)

            jsonArray.put(jsonDeck)
        }

        val saved = preferences.edit()
            .putString("decks", jsonArray.toString())
            .commit()

        if (saved) {
            _decks.value = updatedDecks
        }

        return saved
    }

    suspend fun createDeck(name: String): Boolean =
        withContext(Dispatchers.IO) {

            mutex.withLock {

                val trimmedName = name.trim()

                if (trimmedName.isBlank()) {

                    return@withLock false
                }

                val nextId =
                    (_decks.value.maxOfOrNull { it.id } ?: 0L) + 1L

                val newDeck = Deck(
                    id = nextId,
                    name = trimmedName
                )

                saveDecks(
                    _decks.value + newDeck
                )
            }
        }

    suspend fun deleteDeck(deckId: Long): Boolean =
        withContext(Dispatchers.IO) {

            mutex.withLock {

                if (_decks.value.none { it.id == deckId }) {
                    return@withLock false
                }

                val updatedDecks = _decks.value.filter {
                    it.id != deckId
                }

                saveDecks(updatedDecks)
            }
        }

    suspend fun addCard(
        deckId: Long,
        cardId: String
    ): Boolean = withContext(Dispatchers.IO) {

        mutex.withLock {

            val deck = _decks.value.find {
                it.id == deckId
            } ?: return@withLock false

            if (cardId in deck.cardIds) {
                return@withLock false
            }

            if (collectionRepository.getCardById(cardId) == null) {
                return@withLock false
            }

            val updatedDecks = _decks.value.map {

                if (it.id == deckId) {

                    it.copy(
                        cardIds = it.cardIds + cardId
                    )

                } else {
                    it
                }
            }

            saveDecks(updatedDecks)
        }
    }

    suspend fun removeCard(
        deckId: Long,
        cardId: String
    ): Boolean = withContext(Dispatchers.IO) {

        mutex.withLock {

            val deck = _decks.value.find {
                it.id == deckId
            } ?: return@withLock false

            if (cardId !in deck.cardIds) {
                return@withLock false
            }

            val updatedDecks = _decks.value.map {

                if (it.id == deckId) {

                    it.copy(
                        cardIds = it.cardIds.filterNot {
                                id -> id == cardId
                        }
                    )

                } else {
                    it
                }
            }

            saveDecks(updatedDecks)
        }
    }
}
