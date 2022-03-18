package com.milanga.movietime.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MovieTimeNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    content: @Composable RowScope.() -> Unit
){
    NavigationBar (
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        content = content
    )

}