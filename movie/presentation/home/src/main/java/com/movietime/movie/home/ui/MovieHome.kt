package com.movietime.movie.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.movietime.core.views.highlight.HighlightedSection
import com.movietime.core.views.poster.ListSection
import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.main.views.SectionTitle
import com.movietime.movie.home.R
import com.movietime.movie.home.presentation.MoviesViewModel


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MovieHome(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieSelected: (id: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieHome(
        uiState = uiState,
        onMovieSelected = onMovieSelected,
        onTopRatedMoviesThresholdReached = {viewModel.onTopRatedMoviesThreshold()},
        onUpcomingMoviesThresholdReached = {viewModel.onUpcomingMoviesThreshold()},
        onPopularMoviesThresholdReached = {viewModel.onPopularMoviesThreshold()}
    )
}
@Composable
private fun MovieHome(
    uiState: MoviesViewModel.MoviesUiState,
    onMovieSelected: (id: Int) -> Unit,
    onTopRatedMoviesThresholdReached: () -> Unit,
    onUpcomingMoviesThresholdReached: () -> Unit,
    onPopularMoviesThresholdReached: () -> Unit
){
    when(uiState){
        is MoviesViewModel.MoviesUiState.Error -> ErrorScreen()
        is MoviesViewModel.MoviesUiState.Content -> Content(
            popularMovies = uiState.popularMovies,
            topRatedMovies = uiState.topRatedMovies,
            upcomingMovies = uiState.upcomingMovies,
            moviesWatchlist = uiState.moviesWatchlist,
            onMovieSelected = onMovieSelected,
            onTopRatedMoviesThresholdReached = onTopRatedMoviesThresholdReached,
            onUpcomingMoviesThresholdReached = onUpcomingMoviesThresholdReached,
            onPopularMoviesThresholdReached = onPopularMoviesThresholdReached
        )
        is MoviesViewModel.MoviesUiState.Loading -> Content(loading = true)
    }
}

@Composable
private fun ErrorScreen() {
    Text("error")
}

@Composable
private fun Content(
    popularMovies: List<HighlightedItem> = emptyList(),
    topRatedMovies: List<PosterItem> = emptyList(),
    upcomingMovies: List<PosterItem> = emptyList(),
    moviesWatchlist: List<HighlightedItem> = emptyList(),
    onMovieSelected: (id: Int) -> Unit = {},
    loading: Boolean = false,
    onTopRatedMoviesThresholdReached: () -> Unit = {},
    onUpcomingMoviesThresholdReached: () -> Unit = {},
    onPopularMoviesThresholdReached: () -> Unit = {}
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
                    highlightedList = popularMovies,
                    onItemSelected = onMovieSelected,
                    onScrollThresholdReached = onPopularMoviesThresholdReached,
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
                    posterList = topRatedMovies,
                    onItemSelected = onMovieSelected,
                    onScrollThresholdReached = onTopRatedMoviesThresholdReached,
                    modifier = Modifier.padding(top = 8.dp),
                    loading = loading
                )
            }

            item {
                SectionTitle(
                    stringResource(R.string.upcoming_title),
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading = loading
                )
            }

            item {
                ListSection(
                    posterList = upcomingMovies,
                    onItemSelected = onMovieSelected,
                    onScrollThresholdReached = onUpcomingMoviesThresholdReached,
                    modifier = Modifier.padding(top = 8.dp),
                    loading = loading
                )
            }

            item {
                SectionTitle(
                    stringResource(R.string.watchlist_title),
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading = loading
                )
            }

            item {
                HighlightedSection(
                    highlightedList = moviesWatchlist,
                    onItemSelected = onMovieSelected,
                    onScrollThresholdReached = {},
                    loading = loading,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
    }

}



