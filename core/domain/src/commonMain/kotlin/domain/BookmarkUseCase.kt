package domain

import data.repository.PokemonRepository
import model.SimplePokemon

class BookmarkUseCase(
    private val repository: PokemonRepository,
) {
    suspend operator fun invoke(exist: Boolean, simplePokemon: SimplePokemon) {
        repository.bookmark(exist, simplePokemon)
    }
}
