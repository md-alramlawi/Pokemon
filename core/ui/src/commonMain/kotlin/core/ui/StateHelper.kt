package core.ui

import core.ui.state.UIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StateHelper(private val scope: CoroutineScope) {

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    fun setIdleState() = scope.launch {
        _uiState.update { UIState.Idle }
    }

    fun setLoadingState() = scope.launch {
        _uiState.update { UIState.Loading }
    }

    fun setSuccessState() = scope.launch {
        _uiState.update { UIState.Success }
    }

    fun setCompletedState() = scope.launch {
        _uiState.update { UIState.Completed }
    }

    fun setFailureState(throwable: Throwable) = scope.launch {
        _uiState.update { UIState.Failure(throwable) }
    }

    fun isLoading() = uiState.value is UIState.Loading
}
