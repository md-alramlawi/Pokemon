package pokemon.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.common.result.mapError
import com.pokemon.common.result.mapSuccess
import com.pokemon.data.repository.PokemonRepository
import com.pokemon.model.Pokemon
import com.pokemon.model.UIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private var _data: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val data = _data.asStateFlow()

    private var _uiEvent: MutableStateFlow<UIEvent> = MutableStateFlow(UIEvent.Idle)
    val uiEvent = _uiEvent.asStateFlow()


    fun getDetails() {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.Loading)
            pokemonRepository.getPokemon(pokemonRepository.current.value)
                .also {
                    _uiEvent.emit(UIEvent.Idle)
                }
                .mapSuccess {
                    _data.emit(it)
                }.mapError {
                    _uiEvent.emit(UIEvent.Failure(it))
                }
        }
    }

    fun resetUIEvent() {
        viewModelScope.launch { _uiEvent.emit(UIEvent.Idle) }
    }
}