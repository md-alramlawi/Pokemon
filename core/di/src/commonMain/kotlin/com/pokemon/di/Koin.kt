package com.pokemon.di

import com.pokemon.data.repository.PokemonRepository
import com.pokemon.data.repository.PokemonRepositoryImpl
import com.pokemon.ui.screens.detail.DetailScreenModel
import com.pokemon.ui.screens.home.HomeScreenModel
import com.pokemon.network.datasource.pokemon.PokemonDataSource
import com.pokemon.network.datasource.pokemon.PokemonDataSourceImpl
import com.pokemon.network.service.api.KtorPokemonApi
import com.pokemon.network.service.api.PokemonApi
import com.pokemon.network.service.api.client
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { client }
    single<PokemonApi> { KtorPokemonApi(get()) }
    single<PokemonDataSource> { PokemonDataSourceImpl(get()) }
}
val dataModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
}

val screenModelsModule = module {
    factoryOf(::HomeScreenModel)
    factoryOf(::DetailScreenModel)
}
