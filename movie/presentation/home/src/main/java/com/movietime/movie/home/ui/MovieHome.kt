package com.movietime.movie.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.movietime.core.views.highlight.HighlightedItemView
import com.movietime.core.views.model.PosterItem
import com.movietime.main.views.SectionTitle
import com.movietime.movie.home.R
import com.movietime.movie.home.presentation.MoviesViewModel
import com.movietime.movie.home.presentation.model.HighlightedItem
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
            count = popularMovies.size,
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { page ->
            if (popularMovies.size - page < threshold) {
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
                backdropUrl = popularMovies[page].backdropPath,
                posterUrl = popularMovies[page].posterPath,
                rating = popularMovies[page].rating,
                overview = popularMovies[page].overview
            ) { onMovieSelected(popularMovies[page].id) }
        }

}

