package shared.di.modules

import domain.BookmarkUseCase
import domain.FetchBookmarksUseCase
import domain.FetchPokemonDetailsUseCase
import domain.FetchPokemonPageUseCase
import org.koin.dsl.module

internal val domainModule = module {
    factory { FetchPokemonPageUseCase(get()) }
    factory { FetchBookmarksUseCase(get()) }
    factory { BookmarkUseCase(get()) }
    factory { FetchPokemonDetailsUseCase(get()) }
}
