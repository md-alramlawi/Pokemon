package feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.result.Result
import common.result.flatMapSuccess
import common.result.mapError
import common.result.mapSuccess
import constant.Constants
import core.ui.StateHelper
import domain.BookmarkUseCase
import domain.FetchBookmarksUseCase
import domain.FetchPokemonPageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.SimplePokemon

class HomeViewModel(
    private val fetchPokemonPageUseCase: FetchPokemonPageUseCase,
    private val fetchBookmarksUseCase: FetchBookmarksUseCase,
    private val bookmarkUseCase: BookmarkUseCase,
) : ViewModel() {

    private val stateHelper: StateHelper = StateHelper(viewModelScope)

    internal val uiState = stateHelper.uiState

    private val _data: MutableStateFlow<HomeData> = MutableStateFlow(HomeData())
    internal val data: StateFlow<HomeData> = _data.asStateFlow()

    internal fun fetchInitialData() = viewModelScope.launch {
        stateHelper.setLoadingState()
        fetchBookmarksUseCase.invoke().flatMapSuccess { bookmarks ->
            _data.update { d -> d.copy(bookmarkIds = bookmarks.map { it.id }) }
            if (data.value.pokemonList.isEmpty()) {
                fetchPokemonPageUseCase(offset = 0)
            } else {
                Result.Success(data.value.pokemonList)
            }
        }
            .mapSuccess { pokemonList ->
                stateHelper.setIdleState()
                _data.update { d ->
                    d.copy(
                        hasNext = pokemonList.size == Constants.PAGE_LIMIT,
                        pokemonList = pokemonList,
                    )
                }
            }
            .mapError { error ->
                stateHelper.setFailureState(error)
            }
    }

    internal fun bookmark(simplePokemon: SimplePokemon) = viewModelScope.launch {
        val exist = _data.value.bookmarkIds.contains(simplePokemon.id)
        bookmarkUseCase.invoke(
            exist = exist,
            simplePokemon = simplePokemon,
        )
        _data.update { d ->
            d.copy(bookmarkIds = if (exist) d.bookmarkIds - simplePokemon.id else d.bookmarkIds + simplePokemon.id)
        }
    }

    internal fun loadNextPage() = viewModelScope.launch {
        if (stateHelper.isLoading()) return@launch

        stateHelper.setLoadingState()
        fetchPokemonPageUseCase.invoke()
            .mapSuccess { pokemonList ->
                stateHelper.setIdleState()
                _data.update { d ->
                    d.copy(
                        hasNext = pokemonList.size == Constants.PAGE_LIMIT,
                        pokemonList = d.pokemonList + pokemonList,
                    )
                }
            }
            .mapError { error ->
                stateHelper.setFailureState(error)
            }
    }

    internal fun changeSearchQuery(query: String) = viewModelScope.launch {
        _data.update { d -> d.copy(searchQuery = query) }
    }

    internal fun releaseState() = stateHelper.setIdleState()
}

internal data class HomeData(
    val pokemonList: List<SimplePokemon> = emptyList(),
    val searchQuery: String = "",
    val bookmarkIds: List<String> = emptyList(),
    val hasNext: Boolean? = null,
)

internal fun List<SimplePokemon>.search(searchQuery: String): List<SimplePokemon> {
    return this.filter { it.name.contains(searchQuery, true) || it.id.contains(searchQuery, true) }
}
