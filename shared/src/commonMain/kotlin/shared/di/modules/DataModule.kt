package shared.di.modules

import core.network.PokemonDataSource
import data.repository.PokemonRepository
import data.repository.PokemonRepositoryImpl
import database.datasource.LocalDatasource
import org.koin.dsl.module
import shared.Greeting

val dataModule = module {
    single { Greeting() }
    single<LocalDatasource> { LocalDatasource.create() }
    single<PokemonDataSource> { PokemonDataSource.create() }
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
}