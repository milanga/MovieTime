package com.movietime.auth.presentation

import androidx.lifecycle.ViewModel
import com.movietime.domain.interactors.auth.GetAuthorizationRedirectUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getAuthorizationRedirectUri: GetAuthorizationRedirectUri
): ViewModel() {
    private val _authUiState = MutableStateFlow<AuthUiState>(AuthUiState.NoRedirect)
    val authUiState = _authUiState

    fun onLoginClicked() {
        _authUiState.value = AuthUiState.RedirectToLogin(getAuthorizationRedirectUri())
    }

    fun onUserRedirected() {
        _authUiState.value = AuthUiState.NoRedirect
    }
}