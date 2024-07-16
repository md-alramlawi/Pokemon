package detail

import androidx.lifecycle.viewModelScope
import common.result.mapError
import common.result.mapSuccess
import data.mapper.toSimple
import data.repository.PokemonRepository
import model.Pokemon
import ui.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    private var _data: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val data = _data.asStateFlow()

    val bookmarkIds = pokemonRepository.getBookmarks()
        .map { bookmarks ->
            bookmarks.map { it.id }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        getPokemonDetails()
    }

    private fun getPokemonDetails() {
        viewModelScope.launch(ioDispatcher) {
            showLoader(true)
            pokemonRepository.getPokemon(pokemonRepository.currentName.value)
                .also {
                    showLoader(false)
                }
                .mapSuccess {
                    _data.emit(it)
                }.mapError {
                    fireError(it)
                }
        }
    }

    fun bookmark(pokemon: Pokemon) {
        viewModelScope.launch {
            pokemonRepository.bookmark(pokemon.toSimple).mapError {
                fireError(it)
            }
        }
    }
}