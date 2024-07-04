package pokemon.feature.favorite

import androidx.lifecycle.viewModelScope
import common.result.mapError
import data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui.BaseViewModel

class FavoriteViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    val list = pokemonRepository.getBookmarks().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )


    fun setCurrent(id: String) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.setCurrent(id)
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
