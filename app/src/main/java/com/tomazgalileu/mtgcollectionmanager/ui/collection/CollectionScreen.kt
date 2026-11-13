package com.tomazgalileu.mtgcollectionmanager.ui.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CollectionScreen(
    cards: List<CollectionCardUi>,
    onCardClick: (String) -> Unit,
    onIncreaseQuantity: (String) -> Unit,
    onDecreaseQuantity: (String) -> Unit,
    onDeleteCard: (String) -> Unit,
    onBack: () -> Unit
) {
    var cardToDeleteId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val cardToDelete = cards.find {
        it.id == cardToDeleteId
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Voltar")
        }

        Text(
            text = "Minha Coleção",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (cards.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sua coleção está vazia.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = cards,
                    key = { it.id }
                ) { card ->
                    CollectionCard(
                        card = card,
                        onCardClick = onCardClick,
                        onIncreaseQuantity = onIncreaseQuantity,
                        onDecreaseQuantity = onDecreaseQuantity,
                        onDeleteClick = { id ->
                            cardToDeleteId = id
                        }
                    )
                }
            }
        }
    }

    if (cardToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                cardToDeleteId = null
            },
            title = {
                Text("Excluir carta")
            },
            text = {
                Text(
                    "Deseja excluir ${cardToDelete.name} da sua coleção?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        cardToDeleteId = null
                        onDeleteCard(cardToDelete.id)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Excluir")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        cardToDeleteId = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionScreenPreview() {
    var sampleCards by remember {
        mutableStateOf(
            listOf(
                CollectionCardUi(
                    id = "1",
                    name = "Black Lotus",
                    quantity = 2
                ),
                CollectionCardUi(
                    id = "2",
                    name = "Lightning Bolt",
                    quantity = 4
                ),
                CollectionCardUi(
                    id = "3",
                    name = "Counterspell",
                    quantity = 1
                )
            )
        )
    }

    MaterialTheme(colorScheme = darkColorScheme()) {
        CollectionScreen(
            cards = sampleCards,

            onCardClick = {},

            onIncreaseQuantity = { id ->
                sampleCards = sampleCards.map { card ->
                    if (
                        card.id == id &&
                        card.quantity < Int.MAX_VALUE
                    ) {
                        card.copy(
                            quantity = card.quantity + 1
                        )
                    } else {
                        card
                    }
                }
            },

            onDecreaseQuantity = { id ->
                sampleCards = sampleCards.map { card ->
                    if (card.id == id && card.quantity > 1) {
                        card.copy(
                            quantity = card.quantity - 1
                        )
                    } else {
                        card
                    }
                }
            },

            onDeleteCard = { id ->
                sampleCards = sampleCards.filterNot { card ->
                    card.id == id
                }
            },

            onBack = {}
        )
    }
}
