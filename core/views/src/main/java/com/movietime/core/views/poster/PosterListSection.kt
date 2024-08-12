package com.movietime.core.views.poster

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.movietime.core.views.poster.model.PosterItem
import kotlin.math.ceil


@Composable
fun ListSection(
    modifier: Modifier = Modifier,
    posterList: List<PosterItem> = emptyList(),
    onScrollThresholdReached: () -> Unit = {},
    loading: Boolean = false,
    posterItemView: @Composable (PosterItem) -> Unit = { }
) {
    if (loading) {
        LoadingPosterList(modifier)
    } else {
        PosterList(
            posterList = posterList,
            modifier = modifier,
            onScrollThresholdReached = onScrollThresholdReached,
            threshold = 5,
            posterItemView = posterItemView
        )
    }
}

@Composable
fun LoadingPosterList(
    modifier: Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val posterWidth = 130
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp),
    ) {
        for (i in 0..ceil((screenWidth / posterWidth).toDouble()).toInt()) {
            PosterItemView(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(posterWidth.dp)
                    .aspectRatio(0.67f),
                loading = true
            )
        }
    }
}

@Composable
fun PosterList(
    posterList: List<PosterItem>,
    modifier: Modifier = Modifier,
    onScrollThresholdReached: () -> Unit = {},
    threshold: Int = 5,
    posterItemView: @Composable (PosterItem) -> Unit = { }
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        itemsIndexed(items = posterList) { index, posterItem ->
            if (posterList.size - index < threshold) {
                onScrollThresholdReached.invoke()
            }
            posterItemView(posterItem)
        }
    }
}


