package domain

import common.result.Result
import data.repository.PokemonRepository
import model.SimplePokemon

class FetchPokemonPageUseCase(
    private val repository: PokemonRepository,
) {
    suspend operator fun invoke(offset: Int? = null): Result<List<SimplePokemon>> = repository.getPokemonPage(offset)
}
