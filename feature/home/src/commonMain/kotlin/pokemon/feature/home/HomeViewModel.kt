package pokemon.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.common.result.mapError
import com.pokemon.common.result.mapSuccess
import com.pokemon.data.repository.PokemonRepository
import com.pokemon.model.SimplePokemon
import com.pokemon.model.UIEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private var _list: MutableStateFlow<List<SimplePokemon>> = MutableStateFlow(emptyList())
    val list = _list.asStateFlow()

    private var _uiEvent: MutableStateFlow<UIEvent> = MutableStateFlow(UIEvent.Idle)
    val uiEvent = _uiEvent.asStateFlow()

    fun getList() {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.Loading)
            pokemonRepository.getPokemonList()
                .also {
                    _uiEvent.emit(UIEvent.Idle)
                }
                .mapSuccess {
                    _list.emit(it)
                }.mapError {
                    _uiEvent.emit(UIEvent.Failure(it))
                }
        }
    }

    fun setCurrent(name: String){
        viewModelScope.launch {
            pokemonRepository.setCurrent(name)
        }
    }

    fun resetUIEvent(){
        viewModelScope.launch { _uiEvent.emit(UIEvent.Idle) }
    }
}
