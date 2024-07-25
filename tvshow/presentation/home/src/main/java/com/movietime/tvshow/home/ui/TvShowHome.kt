package com.movietime.tvshow.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.core.views.HighlightedSection
import com.movietime.core.views.ListSection
import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.main.views.SectionTitle
import com.movietime.tvshow.home.R
import com.movietime.tvshow.home.presentation.TvShowsViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun TvShowHome(
    viewModel: TvShowsViewModel = hiltViewModel(),
    onTvShowSelected: (id: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TvShowHome(
        uiState = uiState,
        onTvShowSelected = onTvShowSelected,
        onTopRatedTvShowsThresholdReached = {viewModel.onTopRatedTvShowsThreshold()},
        onOnTheAirTvShowsThresholdReached = {viewModel.onOnTheAirTvShowsThreshold()},
        onPopularTvShowsThresholdReached = {viewModel.onPopularTvShowsThreshold()}
    )
}
@Composable
private fun TvShowHome(
    uiState: TvShowsViewModel.TvShowsUiState,
    onTvShowSelected: (id: Int) -> Unit,
    onTopRatedTvShowsThresholdReached: () -> Unit,
    onOnTheAirTvShowsThresholdReached: () -> Unit,
    onPopularTvShowsThresholdReached: () -> Unit
){
    when(uiState){
        is TvShowsViewModel.TvShowsUiState.Error -> ErrorScreen()
        is TvShowsViewModel.TvShowsUiState.Content -> Content(
            popularTvShows = uiState.popularTvShows,
            topRatedTvShows = uiState.topRatedTvShows,
            upcomingTvShows = uiState.onTheAirTvShows,
            onTvShowSelected = onTvShowSelected,
            onTopRatedTvShowsThresholdReached = onTopRatedTvShowsThresholdReached,
            onUpcomingTvShowsThresholdReached = onOnTheAirTvShowsThresholdReached,
            onPopularTvShowsThresholdReached = onPopularTvShowsThresholdReached
        )
        is TvShowsViewModel.TvShowsUiState.Loading -> Content(loading = true)
    }
}

@Composable
private fun ErrorScreen() {
    Text("error")
}

@Composable
private fun Content(
    popularTvShows: List<HighlightedItem> = emptyList(),
    topRatedTvShows: List<PosterItem> = emptyList(),
    upcomingTvShows: List<PosterItem> = emptyList(),
    onTvShowSelected: (id: Int) -> Unit = {},
    loading: Boolean = false,
    onTopRatedTvShowsThresholdReached: () -> Unit = {},
    onUpcomingTvShowsThresholdReached: () -> Unit = {},
    onPopularTvShowsThresholdReached: () -> Unit = {}
){
    Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
        Spacer(
            Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp), MaterialTheme.colorScheme.surface ), startY = 200f))
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentPadding = PaddingValues(bottom = 16.dp, top = 40.dp)
        ) {
            item {
                HighlightedSection(
                    highlightedList = popularTvShows,
                    onItemSelected = onTvShowSelected,
                    onScrollThresholdReached = onPopularTvShowsThresholdReached,
                    loading = loading
                )
            }

            item {
                SectionTitle(
                    stringResource(R.string.top_rated_title),
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading = loading
                )
            }

            item {
                ListSection(
                    posterList = topRatedTvShows,
                    onItemSelected = onTvShowSelected,
                    onScrollThresholdReached = onTopRatedTvShowsThresholdReached,
                    modifier = Modifier.padding(top = 8.dp),
                    loading = loading
                )
            }

            item {
                SectionTitle(
                    stringResource(R.string.on_the_air_title),
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading = loading
                )
            }

            item {
                ListSection(
                    posterList = upcomingTvShows,
                    onItemSelected = onTvShowSelected,
                    onScrollThresholdReached = onUpcomingTvShowsThresholdReached,
                    modifier = Modifier.padding(top = 8.dp),
                    loading = loading
                )
            }
        }
    }

}



