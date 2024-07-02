package com.pokemon.shared

import com.pokemon.common.definition.viewModelDefinition
import com.pokemon.common.di.AppDispatchers
import com.pokemon.common.di.CoroutineModule
import com.pokemon.data.di.DataModule
import com.pokemon.network.di.NetworkModule
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pokemon.feature.detail.DetailViewModel
import pokemon.feature.home.HomeViewModel

object ViewModelsModule {
    operator fun invoke() = module {
        viewModelDefinition { HomeViewModel(get(), get(named(AppDispatchers.IO))) }
        viewModelDefinition { DetailViewModel(get(), get(named(AppDispatchers.IO))) }
    }
}

fun initKoin() {
    startKoin {
        modules(
            CoroutineModule(),
            NetworkModule(),
            DataModule(),
            ViewModelsModule(),
        )
    }
}