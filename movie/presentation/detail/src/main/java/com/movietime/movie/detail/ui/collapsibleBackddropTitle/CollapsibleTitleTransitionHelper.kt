package com.movietime.movie.detail.ui.collapsibleBackddropTitle

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.util.lerp

internal class CollapsibleTitleTransitionHelper(
    private val listState: LazyListState,
    private val textWidth: () -> Int,
    initialFontSize: Float,
    finalFontSize: Float,
    private val finalTransitionOffset: Int,
    private val finalTranslation: Float
){
    private val finalScale = finalFontSize / initialFontSize
    private val scaleTranslation by lazy{
        textWidth.invoke() * (1-finalScale) / 2
    }

    fun calculateCollapsibleTitleScale() =
        lerp(
            start = 1f,
            stop = finalScale,
            fraction = transitionRatio()
        )

    fun calculateCollapsibleTitleTranslation() =
        lerp(
            start = 0f,
            stop = finalTranslation - scaleTranslation,
            fraction = transitionRatio()
        )

    fun calculateCollapsibleBackdropAlpha() =
        lerp(
            start = 1f,
            stop = 0f,
            fraction = transitionRatio()
        )

    fun parallaxOffset() = listState.firstVisibleItemScrollOffset / 2

    private fun transitionRatio() =
        listState.firstVisibleItemScrollOffset.toFloat() /
                (listState.layoutInfo.visibleItemsInfo[0].size - finalTransitionOffset).coerceAtLeast(1)

}