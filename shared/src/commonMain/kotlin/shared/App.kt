package shared

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import core.ui.theme.Gray95
import core.ui.theme.PokemonTheme
import feature.detail.DetailsScreen
import feature.favorite.FavoriteScreen
import feature.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
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
        modifier = Modifier.fillMaxSize().background(Gray95),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500),
            )
        },
    ) {
        composable(route = "home") {
            HomeScreen(
                onClickItem = {
                    navController.navigate("details") { launchSingleTop = true }
                },
                onGoFavorite = {
                    navController.navigate("favorite") { launchSingleTop = true }
                },
            )
        }
        composable(route = "details") {
            DetailsScreen(
                onBack = navController::navigateUp,
            )
        }
        composable(route = "favorite") {
            FavoriteScreen(
                onClickItem = {
                    navController.navigate("details") { launchSingleTop = true }
                },
                onBack = navController::navigateUp,
            )
        }
    }
}
