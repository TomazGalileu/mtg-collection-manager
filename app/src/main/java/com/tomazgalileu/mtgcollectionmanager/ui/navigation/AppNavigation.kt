package com.tomazgalileu.mtgcollectionmanager.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tomazgalileu.mtgcollectionmanager.ui.home.HomeScreen
import com.tomazgalileu.mtgcollectionmanager.ui.collection.CollectionRoute

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

        composable("search") {
            TemporaryScreen(
                title = "Pesquisar Cartas",
                onBack = { navController.popBackStack() }
            )
        }

        composable("collection") {
            CollectionRoute(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("decks") {
            TemporaryScreen(
                title = "Meus Baralhos",
                onBack = { navController.popBackStack() }
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
