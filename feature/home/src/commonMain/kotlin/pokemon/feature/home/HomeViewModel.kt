package pokemon.feature.home

import androidx.lifecycle.viewModelScope
import common.result.mapError
import common.result.mapSuccess
import data.repository.PokemonRepository
import model.SimplePokemon
import ui.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    val bookmarkIds = pokemonRepository.getBookmarks()
        .map { bookmarks ->
            bookmarks.map { it.id }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private var _state: MutableStateFlow<State> =
        MutableStateFlow(State(query = pokemonRepository.searchQuery.value))
    val state = _state.asStateFlow()

    fun getList() {
        viewModelScope.launch(ioDispatcher) {
            showLoader(true)
            pokemonRepository.getPokemonList()
                .also { showLoader(false) }
                .mapSuccess { list ->
                    _state.update { it.copy(list = list) }
                }.mapError {
                    fireErrorMessage(it)
                }
        }
    }

    fun setCurrent(id: String) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.setCurrent(id)
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            pokemonRepository.search(query).also { list ->
                _state.update { it.copy(list = list, query = query) }
            }
        }
    }

    fun bookmark(id: String) {
        viewModelScope.launch {
            pokemonRepository.bookmark(id).mapError {
                fireErrorMessage(it)
            }
        }
    }

    data class State(
        val list: List<SimplePokemon> = emptyList(),
        val query: String = ""
    )
}
