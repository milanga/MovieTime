package com.movietime.movie.detail.ui.collapsibleBackddropTitle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.placeholder

internal data class CollapsableConfig(val finalTransitionOffset: Int, val finalTranslation: Float, val finalFontSize: TextUnit)

@Composable
@OptIn(coil.annotation.ExperimentalCoilApi::class)
internal fun CollapsibleBackdropTitle(
    backdropUrl: String = "",
    title: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.displaySmall,
    loading: Boolean = false,
    collapsableConfig: CollapsableConfig = CollapsableConfig(0,0f, MaterialTheme.typography.displaySmall.fontSize),
    listState: LazyListState = LazyListState()
) {
    val showTitle by rememberShowTitle(listState, collapsableConfig.finalTransitionOffset)
    var textWidth by remember { mutableStateOf(0) }
    val transitionHelper = CollapsibleTitleTransitionHelper(
        listState,
        {textWidth},
        titleStyle.fontSize.value,
        collapsableConfig.finalFontSize.value,
        collapsableConfig.finalTransitionOffset,
        collapsableConfig.finalTranslation
    )

    CollapsibleBackdropTitle(
        backdropUrl,
        title,
        titleStyle,
        transitionHelper,
        loading,
        showTitle
    ) { textWidth = it }
}

@Composable
private fun rememberShowTitle(listState: LazyListState, threshold: Int) = remember {
    derivedStateOf {
        val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
        visibleItemsInfo.isEmpty() || (visibleItemsInfo[0].index == 0 && visibleItemsInfo[0].size + visibleItemsInfo[0].offset > threshold)
    }
}

@Composable
private fun CollapsibleBackdropTitle(
    backdropUrl: String,
    title: String,
    titleStyle: TextStyle,
    transitionHelper: CollapsibleTitleTransitionHelper,
    loading: Boolean,
    showTitle: Boolean,
    onTitleWidthChanged: (Int) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
    ) {

        CollapsibleBackdrop(
            backdropUrl,
            transitionHelper,
            loading
        )

        if(!loading && showTitle) {
            CollapsibleTitle(
                title,
                titleStyle,
                transitionHelper,
                onTitleWidthChanged,
                Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun CollapsibleBackdrop(
    backdropUrl: String,
    transitionHelper: CollapsibleTitleTransitionHelper,
    isLoading: Boolean
){
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = backdropUrl).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .placeholder(
                isLoading,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
            .offset {
                IntOffset(
                    x = 0,
                    y = transitionHelper.parallaxOffset()
                )
            }
            .graphicsLayer {
                if(!isLoading)
                    alpha = transitionHelper.calculateCollapsibleBackdropAlpha()
            }
    )
}

@Composable
private fun CollapsibleTitle(
    title: String,
    titleStyle: TextStyle,
    transitionHelper: CollapsibleTitleTransitionHelper,
    onWidthChanged: (Int)->Unit,
    modifier: Modifier
){
    Text(
        text = title,
        style = titleStyle,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .padding(top = 14.dp, start = 16.dp, end = 16.dp)
            .graphicsLayer {
                val scale = transitionHelper.calculateCollapsibleTitleScale()
                scaleX = scale
                scaleY = scale
                translationX = transitionHelper.calculateCollapsibleTitleTranslationX()
            }
            .onSizeChanged {
                onWidthChanged(it.width)
            }
    )
}

