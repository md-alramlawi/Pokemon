package feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.result.mapError
import data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.SimplePokemon

class FavoriteViewModel(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    val list = pokemonRepository.getBookmarks().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )


    fun setCurrent(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.setCurrent(name)
        }
    }

    fun bookmark(simplePokemon: SimplePokemon) {
        viewModelScope.launch {
            pokemonRepository.bookmark(simplePokemon).mapError {
//                fireError(it)
            }
        }
    }
}
