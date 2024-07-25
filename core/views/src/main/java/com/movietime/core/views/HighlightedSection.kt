package com.movietime.core.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.placeholder.placeholder
import com.movietime.core.views.highlight.HighlightedItemView
import com.movietime.core.views.highlight.model.HighlightedItem
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HighlightedSection(
    highlightedList: List<HighlightedItem> = listOf(),
    onItemSelected: (id: Int) -> Unit = {},
    onScrollThresholdReached: () -> Unit = {},
    threshold: Int = 5,
    loading: Boolean = false
) {
    val modifier = if(loading){
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .aspectRatio(1.78f)
            .placeholder(true, MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp), RoundedCornerShape(18.dp))

    } else {
        Modifier
            .fillMaxWidth()
    }
    HorizontalPager(
        count = highlightedList.size,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { page ->
        if (highlightedList.size - page < threshold) {
            onScrollThresholdReached.invoke()
        }
        HighlightedItemView(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.95f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .aspectRatio(1.78f)
                .clip(RoundedCornerShape(18.dp)),
            backdropUrl = highlightedList[page].backdropPath,
            posterUrl = highlightedList[page].posterPath,
            rating = highlightedList[page].rating,
            overview = highlightedList[page].overview
        ) { onItemSelected(highlightedList[page].id) }
    }

}