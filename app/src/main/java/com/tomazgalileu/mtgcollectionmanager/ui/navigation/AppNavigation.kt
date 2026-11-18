
package com.tomazgalileu.mtgcollectionmanager.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tomazgalileu.mtgcollectionmanager.ui.collection.CollectionRoute
import com.tomazgalileu.mtgcollectionmanager.ui.details.CardDetailsScreen
import com.tomazgalileu.mtgcollectionmanager.ui.details.CardDetailsViewModel
import com.tomazgalileu.mtgcollectionmanager.ui.details.createCardDetailsViewModelFactory
import com.tomazgalileu.mtgcollectionmanager.ui.home.HomeScreen
import com.tomazgalileu.mtgcollectionmanager.ui.search.SearchScreen
import com.tomazgalileu.mtgcollectionmanager.ui.search.SearchViewModel
import com.tomazgalileu.mtgcollectionmanager.ui.search.createSearchViewModelFactory

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {

        composable("home") {
            HomeScreen(
                onSearchClick = {
                    navController.navigate("search") {
                        launchSingleTop = true
                    }
                },
                onCollectionClick = {
                    navController.navigate("collection") {
                        launchSingleTop = true
                    }
                },
                onDecksClick = {
                    navController.navigate("decks") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("collection") {
            CollectionRoute(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "cardDetails/{cardId}",
            arguments = listOf(
                navArgument("cardId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val cardId = backStackEntry.arguments
                ?.getString("cardId").orEmpty()

            val detailsViewModel: CardDetailsViewModel = viewModel(
                factory = createCardDetailsViewModelFactory()
            )

            val uiState by detailsViewModel.uiState.collectAsState()

            LaunchedEffect(cardId) {
                detailsViewModel.loadCard(cardId)
            }

            CardDetailsScreen(
                uiState = uiState,
                onRetry = {
                    detailsViewModel.loadCard(cardId)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("search") {

            val searchViewModel: SearchViewModel = viewModel(
                factory = createSearchViewModelFactory()
            )

            val uiState by searchViewModel.uiState.collectAsState()

            SearchScreen(
                uiState = uiState,
                onSearch = { text ->
                    searchViewModel.searchCards(text)
                },
                onCardClick = { cardId ->
                    navController.navigate("cardDetails/$cardId")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("decks") {
            TemporaryScreen(
                title = "Meus Baralhos",
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun TemporaryScreen(
    title: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title)

        TextButton(onClick = onBack) {
            Text("Voltar")
        }
    }
}
