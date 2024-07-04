package pokemon.feature.home

import androidx.lifecycle.viewModelScope
import common.result.mapError
import data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui.BaseViewModel

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    private fun log(message: String) {
        println("HomeViewModel -> $message")
    }

    val bookmarkIds = pokemonRepository.getBookmarks()
        .map { bookmarks ->
            bookmarks.map { it.id }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val list = pokemonRepository.currentList
    val query = pokemonRepository.searchQuery

    init {
        getList()
        viewModelScope.launch {
            uiEvents.collect {
                log("Current Event: $it")
            }
        }
    }

    private fun getList() {
        viewModelScope.launch(ioDispatcher) {
            log("getList")
            showLoader(true)
            pokemonRepository.getPokemonList()
                .also { showLoader(false) }
                .mapError {
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
            pokemonRepository.search(query)
        }
    }

    fun bookmark(id: String) {
        viewModelScope.launch {
            pokemonRepository.bookmark(id).mapError {
                fireErrorMessage(it)
            }
        }
    }
}
