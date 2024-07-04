package pokemon.feature.detail

import androidx.lifecycle.viewModelScope
import common.result.mapError
import common.result.mapSuccess
import data.repository.PokemonRepository
import model.Pokemon
import ui.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    private var _data: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val data = _data.asStateFlow()

    fun getDetails() {
        viewModelScope.launch(ioDispatcher) {
            showLoader(true)
            pokemonRepository.getPokemon(pokemonRepository.currentId.value)
                .also {
                    showLoader(false)
                }
                .mapSuccess {
                    _data.emit(it)
                }.mapError {
                    fireErrorMessage(it)
                }
        }
    }
}