package com.pokemon.network.di

import com.pokemon.network.datasource.pokemon.PokemonDataSource
import com.pokemon.network.datasource.pokemon.PokemonDataSourceImpl
import com.pokemon.network.service.api.KtorPokemonApi
import com.pokemon.network.service.api.PokemonApi
import com.pokemon.network.service.api.client
import io.ktor.client.HttpClient
import org.koin.dsl.module

object NetworkModule {
    operator fun invoke() = module {
        single<HttpClient> { client }
        single<PokemonApi> { KtorPokemonApi(get()) }
        single<PokemonDataSource> { PokemonDataSourceImpl(get()) }
    }
}
