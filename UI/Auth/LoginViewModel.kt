package com.mirzadev.admincenter.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirzadev.admincenter.data.model.AdminNotAuthorizedException
import com.mirzadev.admincenter.data.repository.AdminAuthRepository
import com.mirzadev.admincenter.util.Validators
import com.mirzadev.admincenter.util.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val error: String? = null
)

class LoginViewModel(private val repository: AdminAuthRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value, emailError = null, error = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value, passwordError = null, error = null) }
    }

    fun submit() {
        val current = _state.value
        if (current.loading) return

        val emailError = Validators.validateEmail(current.email)
        val passwordError = if (current.password.isEmpty()) "Password is required." else null
        if (emailError != null || passwordError != null) {
            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        _state.update { it.copy(loading = true, error = null) }
        viewModelScope.launch {
            val result = repository.signIn(current.email, current.password)
            _state.update { s ->
                result.fold(
                    onSuccess = { s.copy(loading = false, password = "") },
                    onFailure = { e ->
                        s.copy(
                            loading = false,
                            password = if (e is AdminNotAuthorizedException) "" else s.password,
                            error = e.toUserMessage()
                        )
                    }
                )
            }
        }
    }
}
