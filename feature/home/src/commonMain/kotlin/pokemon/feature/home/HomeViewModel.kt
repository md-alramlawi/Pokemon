package pokemon.feature.home

import androidx.lifecycle.viewModelScope
import common.result.mapError
import data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.SimplePokemon
import ui.BaseViewModel

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    val bookmarkIds = pokemonRepository.getBookmarks()
        .map { bookmarks ->
            bookmarks.map { it.id }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val list = pokemonRepository.currentList
    val query = pokemonRepository.searchQuery
    val isLoadingMore = MutableStateFlow(false)

    init {
        getList()
    }

    private fun getList() {
        viewModelScope.launch(ioDispatcher) {
            showLoader(true)
            pokemonRepository.getPokemonList()
                .also { showLoader(false) }
                .mapError {
                    fireErrorMessage(it)
                }
        }
    }

    fun setCurrent(name: String) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.setCurrent(name)
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            pokemonRepository.search(query)
        }
    }

    fun bookmark(simplePokemon: SimplePokemon) {
        viewModelScope.launch {
            pokemonRepository.bookmark(simplePokemon).mapError {
                fireErrorMessage(it)
            }
        }
    }

    fun loadMore() {
        if (isLoadingMore.value) {
            return
        }

        viewModelScope.launch {
            isLoadingMore.update { true }
            withContext(ioDispatcher) {
                pokemonRepository.loadNext()
            }.also {
                isLoadingMore.update { false }
            }.mapError {
                fireErrorMessage(it)
            }
        }
    }
}
