package com.movietime.core.views.topbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun TopBar(
    showTopAppBar: Boolean,
    title: String,
    onBackNavigation: () -> Unit,
    onIconWidthChanged: (Int) -> Unit,
    onAppBarHeightChanged: (Int) -> Unit
) {
    val backgroundAlpha by animateFloatAsState(targetValue = if (showTopAppBar) 1f else 0f)
    val statusHeight = with(LocalDensity.current) { WindowInsets.statusBars.getTop(this).toDp() }
    val topBarHeight = 64.dp
    val appBarBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    Box {
        Surface(
            color = appBarBackgroundColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(topBarHeight + statusHeight)
                .graphicsLayer { alpha = backgroundAlpha }
        ) {}

        TopAppBar(
            title = {
                if (showTopAppBar) {
                    Text(title)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent),
            navigationIcon = {
                IconButton(
                    onClick = { onBackNavigation() },
                    modifier = Modifier
                        .background(
                            color = appBarBackgroundColor.copy(alpha = 0.4f),
                            shape = CircleShape
                        )
                        .onSizeChanged { size ->
                            onIconWidthChanged(size.width)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { size ->
                    onAppBarHeightChanged(size.height)
                }
        )
    }
}