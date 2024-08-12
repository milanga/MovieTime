@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.movietime.tvshow.home.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.core.views.SharedElementType
import com.movietime.core.views.SharedKeys
import com.movietime.core.views.highlight.HighlightedSection
import com.movietime.core.views.poster.ListSection
import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.core.views.poster.PosterImage
import com.movietime.core.views.poster.PosterItemView
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.main.views.SectionTitle
import com.movietime.tvshow.home.R
import com.movietime.tvshow.home.presentation.TvShowsViewModel


@Composable
fun TvShowHome(
    viewModel: TvShowsViewModel = hiltViewModel(),
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onTvShowSelected: (id: Int, origin: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TvShowHome(
        transitionScope = transitionScope,
        animatedContentScope = animatedContentScope,
        uiState = uiState,
        onTvShowSelected = onTvShowSelected,
        onTopRatedTvShowsThresholdReached = {viewModel.onTopRatedTvShowsThreshold()},
        onOnTheAirTvShowsThresholdReached = {viewModel.onOnTheAirTvShowsThreshold()},
        onPopularTvShowsThresholdReached = {viewModel.onPopularTvShowsThreshold()},
        onTrendingTvShowsThresholdReached = {viewModel.onTrendingTvShowsThreshold()}
    )
}
@Composable
private fun TvShowHome(
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    uiState: TvShowsViewModel.TvShowsUiState,
    onTvShowSelected: (id: Int, origin: String) -> Unit,
    onTopRatedTvShowsThresholdReached: () -> Unit,
    onOnTheAirTvShowsThresholdReached: () -> Unit,
    onPopularTvShowsThresholdReached: () -> Unit,
    onTrendingTvShowsThresholdReached: () -> Unit
){
    when(uiState){
        is TvShowsViewModel.TvShowsUiState.Error -> ErrorScreen()
        is TvShowsViewModel.TvShowsUiState.Content -> Content(
            transitionScope = transitionScope,
            animatedContentScope = animatedContentScope,
            popularTvShows = uiState.popularTvShows,
            topRatedTvShows = uiState.topRatedTvShows,
            onTheAirTvShows = uiState.onTheAirTvShows,
            watchListTvShows = uiState.watchlistTvShows,
            trendingTvShows = uiState.trendingTvShows,
            onTvShowSelected = onTvShowSelected,
            onTopRatedTvShowsThresholdReached = onTopRatedTvShowsThresholdReached,
            onUpcomingTvShowsThresholdReached = onOnTheAirTvShowsThresholdReached,
            onPopularTvShowsThresholdReached = onPopularTvShowsThresholdReached,
            onTrendingTvShowsThresholdReached = onTrendingTvShowsThresholdReached
        )
        is TvShowsViewModel.TvShowsUiState.Loading -> Content(transitionScope = transitionScope, animatedContentScope = animatedContentScope, loading = true)
    }
}

@Composable
private fun ErrorScreen() {
    Text("error")
}

@Composable
private fun Content(
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    popularTvShows: List<PosterItem> = emptyList(),
    topRatedTvShows: List<PosterItem> = emptyList(),
    onTheAirTvShows: List<PosterItem> = emptyList(),
    watchListTvShows: List<HighlightedItem> = emptyList(),
    trendingTvShows: List<PosterItem> = emptyList(),
    onTvShowSelected: (id: Int, origin: String) -> Unit = {_,_->},
    loading: Boolean = false,
    onTopRatedTvShowsThresholdReached: () -> Unit = {},
    onUpcomingTvShowsThresholdReached: () -> Unit = {},
    onPopularTvShowsThresholdReached: () -> Unit = {},
    onTrendingTvShowsThresholdReached: () -> Unit = {}
){
    Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
        Spacer(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surfaceColorAtElevation(
                                3.dp
                            ), MaterialTheme.colorScheme.surface
                        ), startY = 200f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp, top = 40.dp)
        ) {
            if (watchListTvShows.isNotEmpty()) {
                val watchlistTitle = stringResource(R.string.watachlist_title)
                SectionTitle(
                    watchlistTitle,
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading = loading
                )

                HighlightedSection(
                    highlightedList = watchListTvShows,
                    onItemSelected = { onTvShowSelected(it, watchlistTitle) },
                    loading = loading,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.trending_title),
                trendingTvShows,
                onTvShowSelected,
                onTrendingTvShowsThresholdReached
            )

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.top_rated_title),
                topRatedTvShows,
                onTvShowSelected,
                onTopRatedTvShowsThresholdReached
            )

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.popular_title),
                popularTvShows,
                onTvShowSelected,
                onPopularTvShowsThresholdReached
            )

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.on_the_air_title),
                onTheAirTvShows,
                onTvShowSelected,
                onUpcomingTvShowsThresholdReached
            )
        }
    }

}

@Composable
private fun PosterSection(
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    loading: Boolean,
    sectionTitle: String,
    tvShows: List<PosterItem>,
    onTvShowSelected: (id: Int, origin: String) -> Unit,
    onTvShowThresholdReached: () -> Unit
) {
    SectionTitle(
        sectionTitle,
        Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        loading = loading
    )

    ListSection(
        posterList = tvShows,
        onScrollThresholdReached = onTvShowThresholdReached,
        modifier = Modifier.padding(top = 8.dp),
        loading = loading
    ) { posterItem ->
        PosterItemView(
            Modifier
                .padding(horizontal = 8.dp)
                .width(130.dp)
                .aspectRatio(0.67f),
            posterItem.rating,
            { onTvShowSelected(posterItem.id, sectionTitle) }
        ) { posterShape ->
            with(transitionScope) {
                PosterImage(
                    posterUrl = posterItem.posterUrl,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = SharedKeys(posterItem.id, sectionTitle, SharedElementType.Image)),
                            animatedContentScope
                        )
                        .fillMaxSize()
                        .clip(posterShape)
                )
            }
        }
    }
}



