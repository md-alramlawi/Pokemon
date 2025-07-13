package data.di

import data.repository.PokemonRepository
import data.repository.PokemonRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
}
