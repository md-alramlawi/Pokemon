package com.pokemon.shared

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pokemon.ui.theme.PokemonTheme
import pokemon.feature.detail.DetailsScreen
import pokemon.feature.home.HomeScreen


@Composable
fun App() {
    PokemonTheme(darkTheme = false) {
        NavHostMain()
    }
}

@Composable
private fun NavHostMain(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.fillMaxSize(),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        composable(route = "home") {
            HomeScreen(
                onClickItem = {
                    navController.navigate("details") {
                        this.launchSingleTop = true
                        this.restoreState = true
                    }
                }
            )
        }
        composable(route = "details") {
            DetailsScreen(
                onBack = navController::popBackStack
            )
        }
    }
}