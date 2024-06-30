package com.pokemon.shared

import com.pokemon.common.definition.viewModelDefinition
import com.pokemon.data.di.DataModule
import com.pokemon.network.di.NetworkModule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pokemon.feature.detail.DetailViewModel
import pokemon.feature.home.HomeViewModel

object ViewModelsModule {
    operator fun invoke() = module {
        viewModelDefinition { HomeViewModel(get())}
        viewModelDefinition { DetailViewModel(get())}
    }
}

fun initKoin() {
    startKoin {
        modules(
            NetworkModule(),
            DataModule(),
            ViewModelsModule(),
        )
    }
}