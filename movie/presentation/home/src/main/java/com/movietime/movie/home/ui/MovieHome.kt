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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.placeholder.placeholder
import com.movietime.core.views.ListSection
import com.movietime.core.views.model.PosterItem
import com.movietime.main.views.SectionTitle
import com.movietime.movie.home.R
import com.movietime.movie.home.presentation.MoviesViewModel
import com.movietime.movie.home.presentation.model.HighlightedItem
import com.movietime.movie.home.ui.highlight.HighlightedItem
import kotlin.math.absoluteValue


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
    onMovieSelected: (id: Int) -> Unit = {},
    loading: Boolean = false,
    onTopRatedMoviesThresholdReached: () -> Unit = {},
    onUpcomingMoviesThresholdReached: () -> Unit = {},
    onPopularMoviesThresholdReached: () -> Unit = {}
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Highlighted(
                popularMovies,
                onMovieSelected,
                onPopularMoviesThresholdReached,
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
                onMovieSelected = onMovieSelected,
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
                onMovieSelected = onMovieSelected,
                onScrollThresholdReached = onUpcomingMoviesThresholdReached,
                modifier = Modifier.padding(top = 8.dp),
                loading = loading
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Highlighted(
    popularMovies: List<HighlightedItem> = listOf(),
    onMovieSelected: (id: Int) -> Unit = {},
    onScrollThresholdReached: () -> Unit = {},
    threshold: Int = 5,
    loading: Boolean = false
) {
    HorizontalPager(
        count = popularMovies.size,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
            .placeholder(
                loading,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
    ) { page ->
        if (popularMovies.size - page < threshold){
            onScrollThresholdReached.invoke()
        }
        HighlightedItem(
            backdropUrl = popularMovies[page].backdropPath,
            posterUrl = popularMovies[page].posterPath,
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
                .fillMaxHeight(),
            title = popularMovies[page].title,
            overview = popularMovies[page].overview,
            rating = popularMovies[page].rating,
            onClick = { onMovieSelected(popularMovies[page].id) }
        )
    }
}

