package com.tomazgalileu.mtgcollectionmanager.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CollectionUiState(
    val cards: List<CollectionCardUi> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class CollectionViewModel(
    private val repository: CollectionRepository
) : ViewModel() {

    val uiState: StateFlow<CollectionUiState> =
        repository.cards
            .map { savedCards ->
                CollectionUiState(
                    cards = savedCards.map { card ->
                        CollectionCardUi(
                            id = card.id,
                            name = card.name,
                            quantity = card.quantity,
                            imageUrl = card.imageUrl
                        )
                    },
                    isLoading = false
                )
            }
            .catch { error ->
                if (error is CancellationException) {
                    throw error
                }

                emit(
                    CollectionUiState(
                        isLoading = false,
                        errorMessage = "Não foi possível carregar a coleção."
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CollectionUiState()
            )

    private val _operationMessage = MutableStateFlow<String?>(null)

    val operationMessage: StateFlow<String?> =
        _operationMessage.asStateFlow()

    fun increaseQuantity(cardId: String) {
        updateQuantity(cardId, increase = true)
    }

    fun decreaseQuantity(cardId: String) {
        updateQuantity(cardId, increase = false)
    }

    private fun updateQuantity(
        cardId: String,
        increase: Boolean
    ) {
        viewModelScope.launch {
            try {
                val updated = if (increase) {
                    repository.increaseQuantity(cardId)
                } else {
                    repository.decreaseQuantity(cardId)
                }

                if (!updated) {
                    _operationMessage.value =
                        "Não foi possível alterar a quantidade."
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _operationMessage.value =
                    "Ocorreu um erro ao atualizar a carta."
            }
        }
    }

    fun clearOperationMessage() {
        _operationMessage.value = null
    }
}
