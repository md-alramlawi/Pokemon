package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ui.state.UIEvent
import ui.state.UserAction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel(
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiEvents = MutableStateFlow<UIEvent>(UIEvent.Idle)
    val uiEvents = _uiEvents.asStateFlow()

    fun onReleaseScreenState() {
        viewModelScope.launch {
            _uiEvents.emit(UIEvent.Idle)
        }
    }

    open fun onRefresh() {}

    fun showSnackbar(message: String) {
        viewModelScope.launch(ioDispatcher) {
            _uiEvents.emit(UIEvent.Toast(message))
            delay(2_000)
            _uiEvents.emit(UIEvent.Idle)
        }
    }

    fun showLoader(loading: Boolean) {
        viewModelScope.launch {
            if (loading)
                _uiEvents.update { UIEvent.Loading }
            else
                _uiEvents.update { UIEvent.Idle }
        }
    }

    fun mergeUIEvent(event: UIEvent) {
        viewModelScope.launch(ioDispatcher) {
            when (event) {
                is UIEvent.Toast -> showSnackbar(event.message)
                is UIEvent.Error -> fireError(Exception(event.message))
                is UIEvent.Loading -> showLoader(true)
                is UIEvent.Idle -> onReleaseScreenState()
            }
        }
    }

    fun fireError(exc: Exception) {
        viewModelScope.launch(ioDispatcher) {
            _uiEvents.emit(UIEvent.Error(exc.message ?: "Something went wrong"))
        }
    }

    open fun onAction(action: UserAction) {
        viewModelScope.launch(ioDispatcher) {
            when (action) {
                UserAction.Refresh -> onRefresh()
                UserAction.Release -> onReleaseScreenState()
            }
        }
    }
}