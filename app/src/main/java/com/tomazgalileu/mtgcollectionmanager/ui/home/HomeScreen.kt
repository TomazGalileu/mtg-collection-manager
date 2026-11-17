package com.tomazgalileu.mtgcollectionmanager.ui.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onCollectionClick: () -> Unit,
    onDecksClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MTG Collection Manager")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSearchClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pesquisar Cartas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCollectionClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Minha Coleção")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDecksClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Meus Baralhos")
        }
    }
}
