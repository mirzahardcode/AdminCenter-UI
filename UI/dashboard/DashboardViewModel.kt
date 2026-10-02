package com.mirzadev.admincenter.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirzadev.admincenter.data.model.DashboardStats
import com.mirzadev.admincenter.data.repository.UserRepository
import com.mirzadev.admincenter.util.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Failed(val message: String) : DashboardUiState
    data class Content(val stats: DashboardStats, val refreshing: Boolean = false) : DashboardUiState
}

class DashboardViewModel(private val users: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val state: StateFlow<DashboardUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch { users.revision.collectLatest { load() } }
    }

    fun refresh() {
        users.invalidate()
    }

    private suspend fun load() {
        _state.update {
            if (it is DashboardUiState.Content) it.copy(refreshing = true) else DashboardUiState.Loading
        }
        users.getUsers().fold(
            onSuccess = { list ->
                _state.value = DashboardUiState.Content(
                    DashboardStats.from(list, System.currentTimeMillis())
                )
            },
            onFailure = { e -> _state.value = DashboardUiState.Failed(e.toUserMessage()) }
        )
    }
}
