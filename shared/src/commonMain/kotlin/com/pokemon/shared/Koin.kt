package com.pokemon.shared

import com.pokemon.di.dataModule
import com.pokemon.di.networkModule
import com.pokemon.di.screenModelsModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            networkModule,
            dataModule,
            screenModelsModule,
        )
    }
}