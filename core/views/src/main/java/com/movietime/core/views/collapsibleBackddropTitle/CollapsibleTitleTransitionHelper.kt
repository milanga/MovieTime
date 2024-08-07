package com.movietime.core.views.collapsibleBackddropTitle

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

    fun calculateCollapsibleTitleTranslationX() =
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

    private fun transitionRatio(): Float {
        if(listState.layoutInfo.visibleItemsInfo.isEmpty()){
            return 0f
        }
        val firstItemSize = listState.layoutInfo.visibleItemsInfo[0].size
        return listState.firstVisibleItemScrollOffset.toFloat() /
                ( firstItemSize - finalTransitionOffset).coerceAtLeast(1)
    }

}