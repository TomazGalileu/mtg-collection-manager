package com.tomazgalileu.mtgcollectionmanager.ui.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun CollectionCard(
    card: CollectionCardUi,
    onCardClick: (String) -> Unit,
    onIncreaseQuantity: (String) -> Unit,
    onDecreaseQuantity: (String) -> Unit,
    onDeleteClick: (String) -> Unit
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
                    .clickable {
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
                    Text("IMG")
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

