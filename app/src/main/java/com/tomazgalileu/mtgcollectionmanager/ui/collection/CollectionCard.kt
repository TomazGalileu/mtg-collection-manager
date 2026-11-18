package com.tomazgalileu.mtgcollectionmanager.ui.collection

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest

@Composable
fun CollectionCard(
    card: CollectionCardUi,
    onCardClick: (String) -> Unit,
    onIncreaseQuantity: (String) -> Unit,
    onDecreaseQuantity: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    enableCardNavigation: Boolean = true,
    enableDeletion: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = enableCardNavigation) {
                        onCardClick(card.id)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp, 84.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (card.imageUrl.isNullOrBlank()) {
                        Text("Sem imagem")
                    } else {
                        val context = LocalContext.current

                        val headers = NetworkHeaders.Builder()
                            .set(
                                "User-Agent",
                                "MTGCollectionManager/1.0 (Android academic project)"
                            )
                            .set(
                                "Accept",
                                "image/jpeg,image/png,image/*;q=0.9,*/*;q=0.8"
                            )
                            .build()

                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(card.imageUrl)
                                .httpHeaders(headers)
                                .build(),
                            contentDescription = "Imagem da carta ${card.name}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit,
                            onError = { error ->
                                Log.e(
                                    "CollectionImage",
                                    "Erro ao carregar imagem de ${card.name}",
                                    error.result.throwable
                                )
                            },
                            onSuccess = {
                                Log.d(
                                    "CollectionImage",
                                    "Imagem carregada: ${card.name}"
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = card.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quantidade: ${card.quantity}",
                    modifier = Modifier.weight(1f)
                )

                TextButton(
                    onClick = {
                        onDecreaseQuantity(card.id)
                    },
                    enabled = card.quantity > 1,
                    modifier = Modifier.semantics {
                        contentDescription =
                            "Diminuir quantidade de ${card.name}"
                    }
                ) {
                    Text("−")
                }

                TextButton(
                    onClick = {
                        onIncreaseQuantity(card.id)
                    },
                    enabled = card.quantity < Int.MAX_VALUE,
                    modifier = Modifier.semantics {
                        contentDescription =
                            "Aumentar quantidade de ${card.name}"
                    }
                ) {
                    Text("+")
                }
            }

            if (enableDeletion) {
                TextButton(
                    onClick = {
                        onDeleteClick(card.id)
                    },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Excluir")
                }
            }
        }
    }
}


