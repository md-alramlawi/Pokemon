package pokemon.feature.home

import androidx.lifecycle.viewModelScope
import com.pokemon.common.result.mapError
import com.pokemon.common.result.mapSuccess
import com.pokemon.data.repository.PokemonRepository
import com.pokemon.model.SimplePokemon
import com.pokemon.ui.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher) {

    private var _state: MutableStateFlow<State> =
        MutableStateFlow(State(query = pokemonRepository.searchQuery.value))
    val state = _state.asStateFlow()

    fun getList() {
        viewModelScope.launch(ioDispatcher) {
            showLoader(true)
            pokemonRepository.getPokemonList()
                .also { showLoader(false) }
                .mapSuccess { list ->
                    _state.update { s -> s.copy(list = list) }
                }.mapError {
                    fireErrorMessage(it)
                }
        }
    }

    fun setCurrent(name: String) {
        viewModelScope.launch(ioDispatcher) {
            pokemonRepository.setCurrent(name)
        }
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
