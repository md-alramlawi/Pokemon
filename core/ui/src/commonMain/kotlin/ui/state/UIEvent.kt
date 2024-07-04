package ui.state

sealed interface UIEvent {
    data object Idle : UIEvent
    data object Loading : UIEvent
    data class Error(val message: String) : UIEvent
    data class Toast(val message: String) : UIEvent
}

sealed interface UserAction {
    data object Release : UserAction
    data object Refresh : UserAction
}