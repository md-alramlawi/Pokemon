package shared.di.modules

import core.network.PokemonDataSource
import data.repository.PokemonRepository
import data.repository.PokemonRepositoryImpl
import database.datasource.LocalDatasource
import org.koin.dsl.module
import shared.Greeting

internal val dataModule =
    module {
        single { Greeting() }
        single<LocalDatasource> { LocalDatasource.create(dataBaseFactory = get()) }
        single<PokemonDataSource> { PokemonDataSource.create() }
        single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
    }
