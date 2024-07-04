package com.pokemon.shared

import common.definition.viewModelDefinition
import common.di.AppDispatchers
import common.di.CoroutineModule
import data.di.DataModule
import network.di.NetworkModule
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pokemon.feature.detail.DetailViewModel
import pokemon.feature.favorite.FavoriteViewModel
import pokemon.feature.home.HomeViewModel

private object ViewModelsModule {
    operator fun invoke() = module {
        viewModelDefinition { HomeViewModel(get(), get(named(AppDispatchers.IO))) }
        viewModelDefinition { DetailViewModel(get(), get(named(AppDispatchers.IO))) }
        viewModelDefinition { FavoriteViewModel(get(), get(named(AppDispatchers.IO))) }
    }
}

internal val commonModules = listOf(
    CoroutineModule(),
    NetworkModule(),
    DataModule(),
    ViewModelsModule(),
)