package pokemon.feature.home

import androidx.lifecycle.viewModelScope
import common.result.mapError
import common.result.mapSuccess
import data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.SimplePokemon
import ui.BaseViewModel

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    val bookmarkIds: StateFlow<List<String>> = pokemonRepository.getBookmarks()
        .map { bookmarks -> bookmarks.map { it.id } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val currentList = pokemonRepository.currentList
    val searchQuery = pokemonRepository.searchQuery
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch(ioDispatcher) {
            showLoader(true)
            pokemonRepository.getPokemonList()
                .also { showLoader(false) }
                .mapError { error -> fireError(error) }
        }
    }

    fun setCurrentPokemon(name: String) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.setCurrent(name)
        }
    }

    fun searchItems(query: String) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.search(query)
        }
    }

    fun bookmarkPokemon(simplePokemon: SimplePokemon) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.bookmark(simplePokemon)
                .mapError { error -> fireError(error) }
        }
    }

    fun loadMoreItems() {
        if (isLoadingMore.value) return

        viewModelScope.launch(ioDispatcher) {
            _isLoadingMore.value = true
            withContext(ioDispatcher) {
                pokemonRepository.loadNext()
            }.also {
                _isLoadingMore.value = false
            }.mapError { error ->
                fireError(error)
            }.mapSuccess {
                if (currentList.value.isEmpty()) {
                    loadMoreItems()
                }
            }
        }
    }
}
