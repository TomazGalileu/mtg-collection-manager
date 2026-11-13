package com.tomazgalileu.mtgcollectionmanager.ui.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CollectionScreen(
    cards: List<CollectionCardUi>,
    onCardClick: (String) -> Unit,
    onBack: () -> Unit
) {
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = cards,
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

                        Box(
                            modifier = Modifier
                                .size(60.dp, 84.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("IMG")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = card.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "Quantidade: ${card.quantity}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CollectionScreenPreview() {

    val sampleCards = listOf(
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

    MaterialTheme(colorScheme = darkColorScheme()) {
        CollectionScreen(
            cards = sampleCards,
            onCardClick = {},
            onBack = {}
        )
    }
}

