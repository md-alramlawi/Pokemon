package com.pokemon.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.pokemon.ui.screens.home.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(HomeScreen)
    }
}