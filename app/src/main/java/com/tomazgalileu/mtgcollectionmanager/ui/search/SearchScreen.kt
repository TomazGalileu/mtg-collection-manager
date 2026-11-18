package com.tomazgalileu.mtgcollectionmanager.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import coil3.request.ImageRequest
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders


@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onSearch: (String) -> Unit,
    onCardClick: (String) -> Unit,
    onBack: () -> Unit
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Voltar")
        }

        Text(
            text = "Pesquisar Cartas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Nome da carta em inglês") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onSearch(searchText) },
            enabled = !uiState.isLoading &&
                    searchText.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pesquisar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            uiState.hasSearched && uiState.cards.isEmpty() -> {
                Text("Nenhuma carta encontrada.")
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = uiState.cards,
                        key = { it.id }
                    ) { card ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCardClick(card.id)
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val imageUrl = card.imageUris?.small
                                    ?: card.cardFaces
                                        ?.firstOrNull()
                                        ?.imageUris
                                        ?.small
                                Box(
                                    modifier = Modifier
                                        .size(width = 64.dp, height = 88.dp)
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (imageUrl != null) {

                                        var isLoading by remember(imageUrl) {
                                            mutableStateOf(true)
                                        }

                                        var hasError by remember(imageUrl) {
                                            mutableStateOf(false)
                                        }

                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(imageUrl)
                                                .httpHeaders(
                                                    NetworkHeaders.Builder()
                                                        .set("User-Agent", "MTGCollectionManager/1.0")
                                                        .set("Accept", "image/*")
                                                        .build()
                                                )
                                                .build(),
                                            contentDescription = "Imagem da carta ${card.name}",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier.fillMaxSize(),

                                            onLoading = {
                                                isLoading = true
                                                hasError = false
                                            },

                                            onSuccess = {
                                                isLoading = false
                                                hasError = false

                                                Log.d(
                                                    "CoilDebug",
                                                    "Imagem carregada: $imageUrl"
                                                )
                                            },

                                            onError = { state ->
                                                isLoading = false
                                                hasError = true

                                                Log.e(
                                                    "CoilDebug",
                                                    "Erro ao carregar: $imageUrl",
                                                    state.result.throwable
                                                )
                                            }
                                        )

                                        if (isLoading) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(24.dp),
                                                strokeWidth = 2.dp
                                            )
                                        }

                                        if (hasError) {
                                            Text(
                                                text = "Sem imagem",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }

                                    } else {
                                        Text(
                                            text = "Sem imagem",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = card.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    val type = card.typeLine
                                        ?: card.cardFaces
                                            ?.firstOrNull()
                                            ?.typeLine

                                    if (type != null) {
                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = type,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}
