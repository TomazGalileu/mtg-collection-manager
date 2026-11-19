package com.tomazgalileu.mtgcollectionmanager.ui.decks

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

data class DeckUi(
    val id: Long,
    val name: String
)

@Composable
fun DecksScreen(
    decks: List<DeckUi>,
    onCreateDeck: (String) -> Unit,
    onDeckClick: (Long) -> Unit,
    onDeleteDeck: (Long) -> Unit,
    onBack: () -> Unit
) {

    var showCreateDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var deckName by rememberSaveable {
        mutableStateOf("")
    }

    var deckToDeleteId by rememberSaveable {
        mutableStateOf<Long?>(null)
    }

    val deckToDelete = decks.find {
        it.id == deckToDeleteId
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
            text = "Meus Baralhos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                deckName = ""
                showCreateDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Criar baralho")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (decks.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhum baralho criado.")
            }

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = decks,
                    key = { it.id }
                ) { deck ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onDeckClick(deck.id)
                            }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = deck.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )

                            TextButton(
                                onClick = {
                                    deckToDeleteId = deck.id
                                }
                            ) {
                                Text(
                                    text = "Excluir",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {

        AlertDialog(
            onDismissRequest = {
                showCreateDialog = false
            },

            title = {
                Text("Criar baralho")
            },

            text = {
                OutlinedTextField(
                    value = deckName,
                    onValueChange = {
                        deckName = it
                    },
                    label = {
                        Text("Nome do baralho")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },

            confirmButton = {
                TextButton(
                    enabled = deckName.isNotBlank(),
                    onClick = {
                        onCreateDeck(deckName.trim())
                        showCreateDialog = false
                        deckName = ""
                    }
                ) {
                    Text("Criar")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showCreateDialog = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (deckToDelete != null) {

        AlertDialog(
            onDismissRequest = {
                deckToDeleteId = null
            },

            title = {
                Text("Excluir baralho")
            },

            text = {
                Text(
                    "Deseja excluir o baralho ${deckToDelete.name}?"
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteDeck(deckToDelete.id)
                        deckToDeleteId = null
                    }
                ) {
                    Text(
                        text = "Excluir",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        deckToDeleteId = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
