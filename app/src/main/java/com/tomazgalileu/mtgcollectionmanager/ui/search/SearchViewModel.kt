package com.tomazgalileu.mtgcollectionmanager.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomazgalileu.mtgcollectionmanager.data.remote.ScryfallCard
import com.tomazgalileu.mtgcollectionmanager.data.repository.ScryfallRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class SearchUiState(
    val isLoading: Boolean = false,
    val cards: List<ScryfallCard> = emptyList(),
    val errorMessage: String? = null,
    val hasSearched: Boolean = false
)

class SearchViewModel(
    private val repository: ScryfallRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())

    val uiState: StateFlow<SearchUiState> =
        _uiState.asStateFlow()

    fun searchCards(query: String) {
        val text = query.trim()

        if (text.isBlank()) {
            _uiState.value = SearchUiState(
                errorMessage = "Digite o nome de uma carta."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = SearchUiState(
                isLoading = true,
                hasSearched = true
            )

            try {
                val response = repository.searchCards(text)

                _uiState.value = SearchUiState(
                    cards = response.data,
                    hasSearched = true
                )

            } catch (error: CancellationException) {
                throw error

            } catch (error: HttpException) {
                val message = when (error.code()) {
                    404 -> "Nenhuma carta encontrada."
                    429 -> "Muitas pesquisas. Tente novamente mais tarde."
                    else -> "Erro na Scryfall. Tente novamente."
                }

                _uiState.value = SearchUiState(
                    errorMessage = message,
                    hasSearched = true
                )

            } catch (error: IOException) {
                _uiState.value = SearchUiState(
                    errorMessage = "Sem conexão com a internet.",
                    hasSearched = true
                )

            } catch (error: Exception) {
                _uiState.value = SearchUiState(
                    errorMessage = "Não foi possível realizar a pesquisa.",
                    hasSearched = true
                )
            }
        }
    }
}
