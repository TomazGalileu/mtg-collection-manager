package com.tomazgalileu.mtgcollectionmanager.ui.decks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository
import com.tomazgalileu.mtgcollectionmanager.data.repository.DeckRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DecksViewModel(
    private val deckRepository: DeckRepository,
    collectionRepository: CollectionRepository
) : ViewModel() {

    val decks = deckRepository.decks

    val collectionCards = collectionRepository.cards
        .map { cards ->
            cards.map { card ->
                DeckCardUi(
                    id = card.id,
                    name = card.name
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _operationMessage =
        MutableStateFlow<String?>(null)

    val operationMessage =
        _operationMessage.asStateFlow()

    private val _isSaving = MutableStateFlow(false)

    val isSaving = _isSaving.asStateFlow()

    private fun executeOperation(
        errorMessage: String,
        operation: suspend () -> Boolean
    ) {

        if (_isSaving.value) return

        _isSaving.value = true

        viewModelScope.launch {

            try {

                val success = operation()

                if (!success) {
                    _operationMessage.value = errorMessage
                }

            } catch (error: CancellationException) {

                throw error

            } catch (error: Exception) {

                _operationMessage.value = errorMessage

            } finally {

                _isSaving.value = false
            }
        }
    }

    fun createDeck(name: String) {

        executeOperation(
            errorMessage = "Não foi possível criar o baralho."
        ) {
            deckRepository.createDeck(name)
        }
    }

    fun deleteDeck(deckId: Long) {

        executeOperation(
            errorMessage = "Não foi possível excluir o baralho."
        ) {
            deckRepository.deleteDeck(deckId)
        }
    }

    fun addCard(
        deckId: Long,
        cardId: String
    ) {

        executeOperation(
            errorMessage = "Não foi possível adicionar a carta."
        ) {
            deckRepository.addCard(deckId, cardId)
        }
    }

    fun removeCard(
        deckId: Long,
        cardId: String
    ) {

        executeOperation(
            errorMessage = "Não foi possível remover a carta."
        ) {
            deckRepository.removeCard(deckId, cardId)
        }
    }

    fun clearOperationMessage() {

        _operationMessage.value = null
    }
}
