package database.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.SimplePokemon

interface LocalDatasource {
    suspend fun upsert(pokemon: SimplePokemon)
    suspend fun delete(pokemon: SimplePokemon)
    fun getAll(): Flow<List<SimplePokemon>>

    companion object {
        fun create(): LocalDatasource {
            return FakeLocalDatasource()
        }
    }
}

private class FakeLocalDatasource : LocalDatasource {
    private val pokemonList = MutableStateFlow<List<SimplePokemon>>(emptyList())

    override suspend fun upsert(pokemon: SimplePokemon) {
        pokemonList.update { currentList ->
            val mutableList = currentList.toMutableList()
            val index = mutableList.indexOfFirst { it.id == pokemon.id }
            if (index != -1) {
                mutableList[index] = pokemon
            } else {
                mutableList.add(pokemon)
            }
            mutableList.toList()
        }
    }

    override suspend fun delete(pokemon: SimplePokemon) {
        pokemonList.update { currentList ->
            currentList.filterNot { it.id == pokemon.id }
        }
    }

    override fun getAll(): Flow<List<SimplePokemon>> {
        return pokemonList.asStateFlow()
    }
}