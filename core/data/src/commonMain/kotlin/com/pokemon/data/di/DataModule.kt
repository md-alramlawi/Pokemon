package com.pokemon.data.di

import com.pokemon.data.repository.PokemonRepository
import com.pokemon.data.repository.PokemonRepositoryImpl
import org.koin.dsl.module

object DataModule {
    operator fun invoke() = module {
        single<PokemonRepository> { PokemonRepositoryImpl(get()) }
    }
}
