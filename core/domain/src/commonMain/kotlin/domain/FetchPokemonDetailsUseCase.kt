package domain

import common.result.Result
import data.repository.PokemonRepository
import model.Pokemon

class FetchPokemonDetailsUseCase(
    private val repository: PokemonRepository,
) {
    suspend operator fun invoke(name: String): Result<Pokemon> = repository.getPokemonDetails(name)
}
