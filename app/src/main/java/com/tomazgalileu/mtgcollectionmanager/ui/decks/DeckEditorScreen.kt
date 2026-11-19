package com.tomazgalileu.mtgcollectionmanager.ui.decks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class DeckCardUi(
    val id: String,
    val name: String
)

@Composable
fun DeckEditorScreen(
    deckName: String,
    deckCards: List<DeckCardUi>,
    collectionCards: List<DeckCardUi>,
    onAddCard: (String) -> Unit,
    onRemoveCard: (String) -> Unit,
    onBack: () -> Unit
) {

    var showAddDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val availableCards = collectionCards.filter { collectionCard ->
        deckCards.none { deckCard ->
            deckCard.id == collectionCard.id
        }
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
            text = deckName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Total: ${deckCards.size} cartas",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                showAddDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar carta da coleção")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (deckCards.isEmpty()) {

            Box(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Este baralho está vazio.")
            }

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = deckCards,
                    key = { it.id }
                ) { card ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = card.name,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.titleMedium
                            )

                            TextButton(
                                onClick = {
                                    onRemoveCard(card.id)
                                }
                            ) {
                                Text(
                                    text = "Remover",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {

        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
            },

            title = {
                Text("Selecionar carta")
            },

            text = {

                if (availableCards.isEmpty()) {

                    Text(
                        "Nenhuma carta disponível para adicionar."
                    )

                } else {

                    LazyColumn(
                        modifier = Modifier.heightIn(max = 320.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items(
                            items = availableCards,
                            key = { it.id }
                        ) { card ->

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = card.name,
                                    modifier = Modifier.weight(1f)
                                )

                                TextButton(
                                    onClick = {
                                        onAddCard(card.id)
                                        showAddDialog = false
                                    }
                                ) {
                                    Text("Adicionar")
                                }
                            }
                        }
                    }
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        showAddDialog = false
                    }
                ) {
                    Text("Fechar")
                }
            }
        )
    }
}
