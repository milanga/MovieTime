package com.movietime.main.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    loading: Boolean = false
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
            .placeholder(
                loading,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            ),
        color = MaterialTheme.colorScheme.onSurface
    )
}