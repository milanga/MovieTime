package com.movietime.movie.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.movietime.core.presentation.UIContentState
import com.movietime.core.views.model.PosterItem
import com.movietime.main.views.ListSection
import com.movietime.main.views.SectionTitle
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.home.R
import com.movietime.movie.home.presentation.MoviesViewModel
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
        is MoviesViewModel.MoviesUiState.Error -> ErrorScreen(uiState)
        is MoviesViewModel.MoviesUiState.Content -> Content(
            content = uiState,
            onMovieSelected = onMovieSelected,
            onTopRatedMoviesThresholdReached = onTopRatedMoviesThresholdReached,
            onUpcomingMoviesThresholdReached = onUpcomingMoviesThresholdReached,
            onPopularMoviesThresholdReached = onPopularMoviesThresholdReached
        )
    }
}

@Composable
private fun ErrorScreen(error: MoviesViewModel.MoviesUiState.Error){
    Text("error")
}

@Composable
private fun Content(
    content: MoviesViewModel.MoviesUiState.Content,
    onMovieSelected: (id: Int) -> Unit,
    onTopRatedMoviesThresholdReached: () -> Unit,
    onUpcomingMoviesThresholdReached: () -> Unit,
    onPopularMoviesThresholdReached: () -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            HighlightedSection(
                content.popularMovies,
                onMovieSelected,
                onPopularMoviesThresholdReached
            )
        }

        item {
            SectionTitle(
                stringResource(R.string.top_rated_title),
                Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }

        item {
            MoviesListSection(
                content.topRatedMovies,
                onMovieSelected,
                onTopRatedMoviesThresholdReached
            )
        }

        item {
            SectionTitle(
                stringResource(R.string.upcoming_title),
                Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }

        item {
            MoviesListSection(
                content.upcomingMovies,
                onMovieSelected,
                onUpcomingMoviesThresholdReached
            )
        }
    }
}

@Composable
private fun MoviesListSection(
    uiContentState: UIContentState<List<PosterItem>>,
    onMovieSelected: (id: Int) -> Unit,
    onTopRatedMoviesThresholdReached: () -> Unit
) {
    when (uiContentState) {
        is UIContentState.Loading -> ListSection(loading = true, modifier = Modifier.padding(top = 8.dp))
        is UIContentState.ContentState -> ListSection(
            uiContentState.content,
            onMovieSelected,
            onScrollThresholdReached = onTopRatedMoviesThresholdReached,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun HighlightedSection(popularMoviesContent: UIContentState<List<MoviePreview>>, onMovieSelected: (id: Int) -> Unit, onScrollThresholdReached: () -> Unit){
    when(popularMoviesContent){
        is UIContentState.Loading -> Highlighted(loading = true)
        is UIContentState.ContentState -> Highlighted(popularMoviesContent.content, onMovieSelected, onScrollThresholdReached)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Highlighted(
    popularMovies: List<MoviePreview> = listOf(),
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
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
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

