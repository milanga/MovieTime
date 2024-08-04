package com.movietime.core.views.tag

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TagSection(
    tags: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
    ) {
        tags.forEach {
            item {
                Tag(
                    text = it,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}
