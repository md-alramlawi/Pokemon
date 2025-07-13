package domain

import common.result.Result
import data.repository.PokemonRepository
import model.SimplePokemon

class FetchBookmarksUseCase(
    private val repository: PokemonRepository,
) {
    suspend operator fun invoke(): Result<List<SimplePokemon>> = repository.getBookmarks()
}
