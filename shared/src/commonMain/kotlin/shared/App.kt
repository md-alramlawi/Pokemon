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
import androidx.navigation.toRoute
import core.ui.theme.Gray95
import core.ui.theme.PokemonTheme
import feature.detail.DetailsScreen
import feature.favorite.FavoriteScreen
import feature.home.HomeScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed interface AppDestination

@Serializable
data object Home : AppDestination

@Serializable
data class Details(val name: String) : AppDestination

@Serializable
data object Favorite : AppDestination

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
        startDestination = Home,
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
        composable<Home> {
            HomeScreen(
                onClickItem = { name ->
                    navController.navigate(Details(name = name)) {
                        launchSingleTop = true
                    }
                },
                onGoFavorite = {
                    navController.navigate(Favorite) {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable<Details> { backStackEntry ->
            val details: Details = backStackEntry.toRoute()
            DetailsScreen(
                name = details.name,
                onBack = navController::navigateUp,
            )
        }
        composable<Favorite> {
            FavoriteScreen(
                onClickItem = { name ->
                    navController.navigate(Details(name = name)) {
                        launchSingleTop = true
                    }
                },
                onBack = navController::navigateUp,
            )
        }
    }
}
