package shared.di

import core.network.PokemonDataSource
import shared.Greeting
import data.repository.PokemonRepository
import data.repository.PokemonRepositoryImpl
import database.datasource.LocalDatasource
import feature.home.HomeViewModel
import feature.detail.DetailViewModel
import feature.favorite.FavoriteViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { Greeting() }
    single<LocalDatasource> { LocalDatasource.create() }
    single<PokemonDataSource> { PokemonDataSource.create() }
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::FavoriteViewModel)
}