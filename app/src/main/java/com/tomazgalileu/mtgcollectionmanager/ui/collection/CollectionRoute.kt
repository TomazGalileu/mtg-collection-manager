package com.tomazgalileu.mtgcollectionmanager.ui.collection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomazgalileu.mtgcollectionmanager.data.local.AppDatabase
import com.tomazgalileu.mtgcollectionmanager.data.repository.CollectionRepository

@Composable
fun CollectionRoute(
    onBack: () -> Unit
) {
    val context = LocalContext.current.applicationContext

    val repository = remember(context) {
        CollectionRepository(
            AppDatabase.getDatabase(context).cardDao()
        )
    }

    val factory = remember(repository) {
        createCollectionViewModelFactory(repository)
    }

    val viewModel: CollectionViewModel = viewModel(
        factory = factory
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val operationMessage by
    viewModel.operationMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(operationMessage) {
        operationMessage?.let { message ->
            viewModel.clearOperationMessage()
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = uiState.errorMessage
                                ?: "Erro ao carregar a coleção."
                        )

                        Spacer(Modifier.height(12.dp))

                        TextButton(onClick = onBack) {
                            Text("Voltar")
                        }
                    }
                }

                else -> {
                    CollectionScreen(
                        cards = uiState.cards,
                        onCardClick = {},
                        onIncreaseQuantity =
                            viewModel::increaseQuantity,
                        onDecreaseQuantity =
                            viewModel::decreaseQuantity,
                        onDeleteCard = {},
                        onBack = onBack,
                        enableCardNavigation = false,
                        enableDeletion = false
                    )
                }
            }
        }
    }
}
