package com.tomazgalileu.mtgcollectionmanager.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun CardDetailsScreen(
    uiState: CardDetailsUiState,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("Voltar")
        }

        Text(
            text = "Detalhes da Carta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

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

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tentar novamente")
                }
            }

            else -> {

                val card = uiState.card

                if (card != null) {
                    val imageFaces = card.cardFaces
                        .orEmpty()
                        .filter { it.imageUris?.normal != null }

                    val hasMultipleFaces = imageFaces.size > 1

                    var selectedFaceIndex by rememberSaveable(card.id) {
                        mutableIntStateOf(0)
                    }

                    val selectedFace = imageFaces.getOrNull(
                        selectedFaceIndex
                    )

                    Text(
                        text = selectedFace?.name ?: card.name,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val type = selectedFace?.typeLine
                        ?: card.typeLine
                        ?: card.cardFaces
                            ?.firstOrNull()
                            ?.typeLine

                    if (type != null) {
                        Text(
                            text = type,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val imageUrl = selectedFace?.imageUris?.normal
                        ?: card.imageUris?.normal
                        ?: card.cardFaces
                            ?.firstOrNull()
                            ?.imageUris
                            ?.normal

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(5f / 7f)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    )

                    {

                        if (imageUrl != null) {

                            var isImageLoading by remember(imageUrl) {
                                mutableStateOf(true)
                            }

                            var hasImageError by remember(imageUrl) {
                                mutableStateOf(false)
                            }

                            AsyncImage(
                                model = ImageRequest.Builder(
                                    LocalContext.current
                                )
                                    .data(imageUrl)
                                    .httpHeaders(
                                        NetworkHeaders.Builder()
                                            .set(
                                                "User-Agent",
                                                "MTGCollectionManager/1.0"
                                            )
                                            .set("Accept", "image/*")
                                            .build()
                                    )
                                    .build(),

                                contentDescription =
                                    "Imagem da carta ${card.name}",

                                contentScale = ContentScale.Fit,

                                modifier = Modifier.fillMaxSize(),

                                onLoading = {
                                    isImageLoading = true
                                    hasImageError = false
                                },

                                onSuccess = {
                                    isImageLoading = false
                                    hasImageError = false
                                },

                                onError = {
                                    isImageLoading = false
                                    hasImageError = true
                                }
                            )

                            if (isImageLoading) {
                                CircularProgressIndicator()
                            }

                            if (hasImageError) {
                                Text("Imagem indisponível")
                            }


                        } else {
                            Text("Imagem indisponível")
                        }
                    }

                    if (hasMultipleFaces) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Face ${selectedFaceIndex + 1} de ${imageFaces.size}",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                selectedFaceIndex =
                                    (selectedFaceIndex + 1) % imageFaces.size
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Virar carta")
                        }
                    }

                } else {
                    Text("Nenhuma carta disponível.")
                }
            }
        }
    }
}

