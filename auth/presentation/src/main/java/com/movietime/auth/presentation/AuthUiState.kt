package com.movietime.auth.presentation

sealed interface AuthUiState {
    object NoRedirect : AuthUiState
    data class RedirectToLogin(
        val authUrl: String
    ) : AuthUiState
}