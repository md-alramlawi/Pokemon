package favorite

import androidx.lifecycle.viewModelScope
import common.result.mapError
import data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.SimplePokemon
import ui.BaseViewModel

class FavoriteViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    val list = pokemonRepository.getBookmarks().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )


    fun setCurrent(name: String) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.setCurrent(name)
        }
    }

    fun bookmark(simplePokemon: SimplePokemon) {
        viewModelScope.launch {
            pokemonRepository.bookmark(simplePokemon).mapError {
                fireError(it)
            }
        }
    }
}
