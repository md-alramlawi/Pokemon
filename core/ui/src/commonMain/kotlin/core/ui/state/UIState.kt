package core.ui.state

sealed interface UIState {
    data object Idle : UIState

    data object Loading : UIState

    data object Success : UIState

    data object Completed : UIState

    data class Failure(val throwable: Throwable) : UIState
}
