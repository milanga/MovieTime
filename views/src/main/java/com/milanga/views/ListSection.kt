package com.milanga.movietime.views

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.milanga.views.PosterItemView
import com.milanga.views.model.PosterItem
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlin.math.ceil


@Composable
fun ListSection(
    posterList: List<PosterItem> = emptyList(),
    onMovieSelected: (id: Int) -> Unit = {},
    modifier: Modifier = Modifier,
    onScrollThresholdReached: () -> Unit = {},
    loading: Boolean = false
) {
    if (loading) {
        LoadingList(modifier)
    } else {
        PosterList(posterList, onMovieSelected, modifier, onScrollThresholdReached)
    }
}

@Composable
fun LoadingList(modifier: Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val posterWidth = 130.0

    //make list not scrollable
    val state = rememberLazyListState()
    LaunchedEffect(key1 = state) {
        launch {
            state.scroll(scrollPriority = MutatePriority.PreventUserInput) { awaitCancellation() }
        }
    }

    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        for (i in 0..ceil(screenWidth / posterWidth).toInt()) {
            item {
                PosterItemView(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(130.dp)
                        .aspectRatio(0.67f),
                    loading = true
                )
            }
        }
    }
}

@Composable
fun PosterList(
    posterList: List<PosterItem>,
    onMovieSelected: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    onScrollThresholdReached: () -> Unit = {},
    threshold: Int = 5
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        itemsIndexed(items = posterList) { index, posterItem ->
            if (posterList.size - index < threshold) {
                onScrollThresholdReached.invoke()
            }
            PosterItemView(
                Modifier
                    .padding(horizontal = 8.dp)
                    .width(130.dp)
                    .aspectRatio(0.67f),
                posterItem.posterUrl,
                posterItem.rating,
                { onMovieSelected(posterItem.id) }
            )
        }
    }
}


