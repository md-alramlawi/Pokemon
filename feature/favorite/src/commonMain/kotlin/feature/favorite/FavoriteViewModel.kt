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
import kotlinx.coroutines.launch
import model.SimplePokemon

class FavoriteViewModel(
    private val fetchBookmarksUseCase: FetchBookmarksUseCase,
    private val bookmarksUseCase: BookmarkUseCase,
) : ViewModel() {

    private val stateHelper: StateHelper = StateHelper(viewModelScope)
    internal val uiState = stateHelper.uiState
    private val _bookmarksData: MutableStateFlow<BookmarkData> = MutableStateFlow(BookmarkData())
    val bookmarksData: StateFlow<BookmarkData> = _bookmarksData

    internal fun fetchInitialData() = viewModelScope.launch {
        stateHelper.setLoadingState()
        fetchBookmarksUseCase.invoke()
            .mapSuccess { list ->
                stateHelper.setIdleState()
                _bookmarksData.value = BookmarkData(list)
            }
            .mapError { error ->
                stateHelper.setFailureState(error)
            }
    }
    internal fun bookmark(simplePokemon: SimplePokemon) = viewModelScope.launch {
        bookmarksUseCase.invoke(
            exist = true,
            simplePokemon = simplePokemon,
        )
        _bookmarksData.value = BookmarkData(_bookmarksData.value.list - simplePokemon)
    }

    internal fun releaseState() = stateHelper.setIdleState()
}

data class BookmarkData(
    val list: List<SimplePokemon> = emptyList(),
)
