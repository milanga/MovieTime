package com.movietime.core.views

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun WatchlistFab(
    onClick: () -> Unit,
    add: Boolean
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = if (add) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.primary
        }
    ) {
        val watchlistIcon = if (add) {
            R.drawable.playlist_add
        } else {
            R.drawable.playlist_check
        }
        Icon(painterResource(watchlistIcon), "Add to watchlist")
    }
}