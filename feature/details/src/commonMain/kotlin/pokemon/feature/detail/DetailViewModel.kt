package pokemon.feature.detail

import androidx.lifecycle.viewModelScope
import com.pokemon.common.result.mapError
import com.pokemon.common.result.mapSuccess
import com.pokemon.data.repository.PokemonRepository
import com.pokemon.model.Pokemon
import com.pokemon.ui.BaseViewModel
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
            pokemonRepository.getPokemon(pokemonRepository.current.value)
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