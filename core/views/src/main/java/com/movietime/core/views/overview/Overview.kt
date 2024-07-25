package com.movietime.core.views.overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun Overview(
    overview: String,
    loading: Boolean = false
) {
    val loadingModifier = if(loading){
        Modifier.height(140.dp)
    } else {
        Modifier
    }
    Text(
        text = overview,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = loadingModifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .placeholder(
                loading,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
    )
}