package domain.di

import domain.BookmarkUseCase
import domain.FetchBookmarksUseCase
import domain.FetchPokemonDetailsUseCase
import domain.FetchPokemonPageUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule: Module = module {
    factory { FetchPokemonPageUseCase(get()) }
    factory { FetchBookmarksUseCase(get()) }
    factory { BookmarkUseCase(get()) }
    factory { FetchPokemonDetailsUseCase(get()) }
}
