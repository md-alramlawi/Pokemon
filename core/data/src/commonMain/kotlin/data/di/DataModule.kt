package data.di

import data.repository.PokemonRepository
import data.repository.PokemonRepositoryImpl
import org.koin.dsl.module

object DataModule {
    operator fun invoke() = module {
        single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
    }
}
