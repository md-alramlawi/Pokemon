package feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.result.mapError
import common.result.mapSuccess
import core.ui.StateHelper
import domain.BookmarkUseCase
import domain.FetchBookmarksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.SimplePokemon

class FavoriteViewModel(
    private val fetchBookmarksUseCase: FetchBookmarksUseCase,
    private val bookmarksUseCase: BookmarkUseCase,
) : ViewModel() {

    private val stateHelper: StateHelper = StateHelper(viewModelScope)

    internal val uiState = stateHelper.uiState

    private val _data: MutableStateFlow<BookmarkData> = MutableStateFlow(BookmarkData())
    internal val data: StateFlow<BookmarkData> = _data.asStateFlow()

    internal fun fetchInitialData() = viewModelScope.launch {
        stateHelper.setLoadingState()
        fetchBookmarksUseCase.invoke()
            .mapSuccess { list ->
                stateHelper.setIdleState()
                _data.value = BookmarkData(list)
            }
            .mapError { error ->
                stateHelper.setFailureState(error)
            }
    }
//
//    fun setCurrent(name: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            pokemonRepository.setCurrent(name)
//        }
//    }

    internal fun bookmark(simplePokemon: SimplePokemon) = viewModelScope.launch {
        bookmarksUseCase.invoke(
            exist = true,
            simplePokemon = simplePokemon,
        )
        _data.value = BookmarkData(_data.value.list - simplePokemon)
    }

    internal fun releaseState() = stateHelper.setIdleState()
}

internal data class BookmarkData(
    val list: List<SimplePokemon> = emptyList(),
)
