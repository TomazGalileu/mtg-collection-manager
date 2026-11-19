package com.tomazgalileu.mtgcollectionmanager.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomazgalileu.mtgcollectionmanager.data.mapper.toCardEntity
import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallCard
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository
import com.tomazgalileu.mtgcollectionmanager.data.repository.ScryfallRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class CardDetailsUiState(
    val isLoading: Boolean = true,
    val card: ScryfallCard? = null,
    val errorMessage: String? = null,
    val isSaving: Boolean = false,
    val operationMessage: String? = null,
    val operationSuccess: Boolean = false
)

class CardDetailsViewModel(
    private val repository: ScryfallRepository,
    private val collectionRepository: CollectionRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(CardDetailsUiState())

    val uiState: StateFlow<CardDetailsUiState> =
        _uiState.asStateFlow()

    private var loadJob: Job? = null

    fun loadCard(cardId: String) {

        if (_uiState.value.isSaving) return

        loadJob?.cancel()

        if (cardId.isBlank()) {
            _uiState.value = CardDetailsUiState(
                isLoading = false,
                errorMessage = "Identificador da carta inválido."
            )
            return
        }

        loadJob = viewModelScope.launch {

            _uiState.value = CardDetailsUiState(
                isLoading = true
            )

            try {
                val card = repository.getCardById(cardId)

                _uiState.value = CardDetailsUiState(
                    isLoading = false,
                    card = card
                )

            } catch (error: CancellationException) {
                throw error

            } catch (error: HttpException) {

                val message = when (error.code()) {
                    404 -> "Carta não encontrada."
                    429 -> "Muitas solicitações. Tente novamente mais tarde."
                    else -> "Erro na Scryfall. Tente novamente."
                }

                _uiState.value = CardDetailsUiState(
                    isLoading = false,
                    errorMessage = message
                )

            } catch (_: IOException) {

                _uiState.value = CardDetailsUiState(
                    isLoading = false,
                    errorMessage = "Falha de conexão. Verifique sua internet."
                )

            } catch (_: Exception) {

                _uiState.value = CardDetailsUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar a carta."
                )
            }
        }
    }

    fun addCardToCollection() {

        val card = _uiState.value.card ?: return

        if (_uiState.value.isSaving) return

        _uiState.update {
            it.copy(
                isSaving = true,
                operationMessage = null,
                operationSuccess = false
            )
        }

        viewModelScope.launch {

            try {

                val entity = card.toCardEntity()

                val inserted =
                    collectionRepository.insertNewCard(entity)

                val saved = if (inserted) {
                    true
                } else {
                    collectionRepository.increaseQuantity(card.id)
                }

                val message = when {
                    inserted ->
                        "Carta adicionada à coleção."

                    saved ->
                        "Quantidade da carta aumentada."

                    else ->
                        "Não foi possível adicionar a carta."
                }

                _uiState.update {
                    it.copy(
                        operationMessage = message,
                        operationSuccess = saved
                    )
                }

            } catch (error: CancellationException) {
                throw error

            } catch (_: Exception) {

                _uiState.update {
                    it.copy(
                        operationMessage =
                            "Erro ao salvar a carta na coleção.",
                        operationSuccess = false
                    )
                }

            } finally {

                _uiState.update {
                    it.copy(isSaving = false)
                }
            }
        }
    }

    fun clearOperationMessage() {
        _uiState.update {
            it.copy(operationMessage = null)
        }
    }
}
