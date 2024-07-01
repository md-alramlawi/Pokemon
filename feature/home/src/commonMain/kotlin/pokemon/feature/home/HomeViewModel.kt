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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private var _state: MutableStateFlow<State> =
        MutableStateFlow(State(query = pokemonRepository.searchQuery.value))
    val state = _state.asStateFlow()

    private var _uiEvent: MutableStateFlow<UIEvent> = MutableStateFlow(UIEvent.Idle)
    val uiEvent = _uiEvent.asStateFlow()

    init {
        println("HomeViewModel initialized -> ${_state.value}")
    }

    override fun onCleared() {
        super.onCleared()
        println("HomeViewModel cleared")
    }

    fun getList() {
        viewModelScope.launch {
            _uiEvent.emit(UIEvent.Loading)
            pokemonRepository.getPokemonList()
                .also {
                    _uiEvent.emit(UIEvent.Idle)
                }
                .mapSuccess { list ->
                    _state.update { s -> s.copy(list = list) }
                }.mapError {
                    _uiEvent.emit(UIEvent.Failure(it))
                }
        }
    }

    fun setCurrent(name: String) {
        viewModelScope.launch {
            pokemonRepository.setCurrent(name)
        }
    }

    fun resetUIEvent() {
        viewModelScope.launch { _uiEvent.emit(UIEvent.Idle) }
    }

    fun search(query: String) {
        viewModelScope.launch {
            pokemonRepository.search(query).also { list ->
                _state.update { it.copy(list = list, query = query) }
            }
        }
    }

    data class State(
        val list: List<SimplePokemon> = emptyList(),
        val query: String = ""
    )
}
