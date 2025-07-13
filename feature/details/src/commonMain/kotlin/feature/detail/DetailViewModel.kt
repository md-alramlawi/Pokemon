package feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.result.flatMapSuccess
import common.result.mapError
import common.result.mapSuccess
import core.ui.StateHelper
import domain.BookmarkUseCase
import domain.FetchBookmarksUseCase
import domain.FetchPokemonDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Pokemon
import model.SimplePokemon

class DetailViewModel(
    private val fetchPokemonDetailsUseCase: FetchPokemonDetailsUseCase,
    private val fetchBookmarksUseCase: FetchBookmarksUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    private val stateHelper: StateHelper = StateHelper(viewModelScope)
    internal val uiState = stateHelper.uiState
    private val _detailsData: MutableStateFlow<DetailsData> = MutableStateFlow(DetailsData())
    val detailsData: StateFlow<DetailsData> = _detailsData

    internal fun fetchPokemonDetails(name: String) = viewModelScope.launch {
        if (detailsData.value.pokemon != null) return@launch

        stateHelper.setLoadingState()
        fetchBookmarksUseCase.invoke().flatMapSuccess { bookmarks ->
            _detailsData.update { d -> d.copy(bookmarkIds = bookmarks.map { it.id }) }
            fetchPokemonDetailsUseCase.invoke(name)
        }
            .mapSuccess { pokemon ->
                stateHelper.setIdleState()
                _detailsData.update { d -> d.copy(pokemon = pokemon) }
            }
            .mapError { error ->
                stateHelper.setFailureState(error)
            }
    }

    internal fun bookmark(pokemon: Pokemon) = viewModelScope.launch {
        val exist = _detailsData.value.bookmarkIds.contains(pokemon.id)
        bookmarkUseCase.invoke(
            exist = exist,
            simplePokemon = SimplePokemon(
                id = pokemon.id,
                name = pokemon.name,
                url = pokemon.iconUrl,
            ),
        )
        _detailsData.update { d ->
            d.copy(bookmarkIds = if (exist) d.bookmarkIds - pokemon.id else d.bookmarkIds + pokemon.id)
        }
    }

    internal fun releaseState() = stateHelper.setIdleState()
}

data class DetailsData(
    val pokemon: Pokemon? = null,
    val bookmarkIds: List<String> = emptyList(),
)
