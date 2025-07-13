package core.network.di

import core.network.KtorPokemonApi
import core.network.PokemonApi
import core.network.PokemonDataSource
import core.network.PokemonDataSourceImpl
import core.network.ktorClient
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    factory<HttpClient> { ktorClient() }
    factory<PokemonApi> { KtorPokemonApi(get()) }
    single<PokemonDataSource> { PokemonDataSourceImpl(get()) }
}
