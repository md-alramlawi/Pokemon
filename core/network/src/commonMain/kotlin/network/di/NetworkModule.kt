package network.di

import network.datasource.pokemon.PokemonDataSource
import network.datasource.pokemon.PokemonDataSourceImpl
import network.service.api.KtorPokemonApi
import network.service.api.PokemonApi
import network.service.api.client
import io.ktor.client.HttpClient
import org.koin.dsl.module

object NetworkModule {
    operator fun invoke() = module {
        single<HttpClient> { client }
        single<PokemonApi> { KtorPokemonApi(get()) }
        single<PokemonDataSource> { PokemonDataSourceImpl(get()) }
    }
}
