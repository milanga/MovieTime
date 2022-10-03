package com.movietime.movie.detail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

data class CollapsableConfig(val finalTransitionOffset: Int, val finalTranslation: Float, val finalFontSize: TextUnit)

@Composable
@OptIn(coil.annotation.ExperimentalCoilApi::class)
internal fun CollapsibleBackdropTitle(
    backdropUrl: String = "",
    title: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.displaySmall,
    loading: Boolean = false,
    collapsableConfig: CollapsableConfig = CollapsableConfig(0,0f, MaterialTheme.typography.displaySmall.fontSize),
    listState: LazyListState
) {
    val showTitle by remember {
        derivedStateOf {
            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
            val threshold = collapsableConfig.finalTransitionOffset
            visibleItemsInfo.isEmpty() || (visibleItemsInfo[0].index == 0 && visibleItemsInfo[0].size + visibleItemsInfo[0].offset > threshold)
        }
    }
    var textWidth by remember { mutableStateOf(0) }
    val transitionHelper = CollapsibleTitleTransitionHelper(
        listState,
        {textWidth},
        titleStyle.fontSize.value,
        collapsableConfig.finalFontSize.value,
        collapsableConfig.finalTransitionOffset,
        collapsableConfig.finalTranslation
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
    ) {
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
                    loading,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
                .offset {
                    IntOffset(
                        x = 0,
                        y = transitionHelper.parallaxOffset()
                    )
                }
                .graphicsLayer {
                    alpha = transitionHelper.calculateCollapsibleBackdropAlpha()
                }
        )

        if(!loading && showTitle) {
            Text(
                text = title,
                style = titleStyle,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .padding(top = 14.dp, start = 16.dp, bottom = 7.dp, end = 16.dp)
                    .graphicsLayer {
                        val scale = transitionHelper.calculateCollapsibleTitleScale()
                        scaleX = scale
                        scaleY = scale
                        translationX = transitionHelper.calculateCollapsibleTitleTranslation()
                    }
                    .onSizeChanged {
                        textWidth = it.width
                    }
            )
        }
    }
}

class CollapsibleTitleTransitionHelper(
    private val listState: LazyListState,
    private val textWidth: () -> Int,
    initialFontSize: Float,
    finalFontSize: Float,
    private val finalTransitionOffset: Int,
    private val finalTranslation: Float
){
    private val finalScaleDifference = 1 - (finalFontSize / initialFontSize)
    private val scaleTranslation by lazy{
        textWidth.invoke() * finalScaleDifference / 2
    }

    fun calculateCollapsibleTitleScale() = if (isCollapsibleTitleVisible(listState)) {
        1f - finalScaleDifference * transitionRatio()
    } else {
        1f
    }

    fun calculateCollapsibleTitleTranslation() = if (isCollapsibleTitleVisible(listState)) {
        (finalTranslation - scaleTranslation) * transitionRatio()
    } else {
        0f
    }

    fun calculateCollapsibleBackdropAlpha() = if (isCollapsibleTitleVisible(listState)) {
        1f - listState.firstVisibleItemScrollOffset.toFloat() / (listState.layoutInfo.visibleItemsInfo[0].size - finalTransitionOffset)
    } else {
        0f
    }

    fun parallaxOffset() = listState.firstVisibleItemScrollOffset / 2


    private fun transitionRatio() = listState.firstVisibleItemScrollOffset.toFloat() / (listState.layoutInfo.visibleItemsInfo[0].size - finalTransitionOffset)

    private fun isCollapsibleTitleVisible(listState: LazyListState) = listState.layoutInfo.visibleItemsInfo.isNotEmpty() &&
            listState.layoutInfo.visibleItemsInfo[0].index == 0
}