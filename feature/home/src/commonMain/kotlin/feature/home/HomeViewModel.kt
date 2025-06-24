package feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.result.mapError
import common.result.mapSuccess
import data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.SimplePokemon

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

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
        viewModelScope.launch(Dispatchers.IO) {
//            showLoader(true)
            pokemonRepository.getPokemonList()
//                .also { showLoader(false) }
//                .mapError { error -> fireError(error) }
        }
    }

    fun setCurrentPokemon(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.setCurrent(name)
        }
    }

    fun searchItems(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.search(query)
        }
    }

    fun bookmarkPokemon(simplePokemon: SimplePokemon) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.bookmark(simplePokemon)
//                .mapError { error -> fireError(error) }
        }
    }

    fun loadMoreItems() {
        if (isLoadingMore.value) return

        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingMore.value = true
            withContext(Dispatchers.IO) {
                pokemonRepository.loadNext()
            }.also {
                _isLoadingMore.value = false
            }.mapError { error ->
//                fireError(error)
            }.mapSuccess {
                if (currentList.value.isEmpty()) {
                    loadMoreItems()
                }
            }
        }
    }
}
