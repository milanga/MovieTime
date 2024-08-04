package com.movietime.core.views.tagline

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun Tagline(
    modifier: Modifier = Modifier,
    tagline: String = "",
    loading: Boolean = false,
) {
    Text(
        text = tagline,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .placeholder(
                    loading,
                    MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                )
        )
    )
}