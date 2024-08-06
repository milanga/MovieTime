package com.movietime.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.auth.presentation.AuthUiState
import com.movietime.auth.presentation.AuthViewModel



@Composable
fun AuthDialog(
    authViewModel: AuthViewModel = hiltViewModel(),
    onDialogComplete: () -> Unit,
) {
    val uiState by authViewModel.authUiState.collectAsStateWithLifecycle()
    if (uiState is AuthUiState.RedirectToLogin) {
        val uriHandler = LocalUriHandler.current
        uriHandler.openUri((uiState as AuthUiState.RedirectToLogin).authUrl)
        authViewModel.onUserRedirected()
        onDialogComplete()
    }

        AlertDialog(
            onDismissRequest = { onDialogComplete() },
            title = { Text("Login with Trakt") },
            text = { Text("Login with Trakt to unblock cool features") },
            confirmButton = {
                TextButton(
                    onClick = {
                        authViewModel.onLoginClicked()
                    }
                ) { Text("Login") }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDialogComplete() }
                ) { Text("Cancel") }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        )

}