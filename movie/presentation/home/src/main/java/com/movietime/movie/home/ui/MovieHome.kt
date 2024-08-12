@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.movietime.movie.home.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.movietime.movie.home.R
import com.movietime.movie.home.presentation.MoviesViewModel



@Composable
fun MovieHome(
    viewModel: MoviesViewModel = hiltViewModel(),
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onMovieSelected: (id: Int, origin: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieHome(
        uiState = uiState,
        transitionScope = transitionScope,
        animatedContentScope = animatedContentScope,
        onMovieSelected = onMovieSelected,
        onTopRatedMoviesThresholdReached = {viewModel.onTopRatedMoviesThreshold()},
        onUpcomingMoviesThresholdReached = {viewModel.onUpcomingMoviesThreshold()},
        onPopularMoviesThresholdReached = {viewModel.onPopularMoviesThreshold()},
        onTrendingMoviesThresholdReached = {viewModel.onTrendingMoviesThreshold()},
    )
}
@Composable
private fun MovieHome(
    uiState: MoviesViewModel.MoviesUiState,
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onMovieSelected: (id: Int, origin: String) -> Unit,
    onTopRatedMoviesThresholdReached: () -> Unit,
    onUpcomingMoviesThresholdReached: () -> Unit,
    onPopularMoviesThresholdReached: () -> Unit,
    onTrendingMoviesThresholdReached: () -> Unit
){
    when(uiState){
        is MoviesViewModel.MoviesUiState.Error -> ErrorScreen()
        is MoviesViewModel.MoviesUiState.Content -> Content(
            transitionScope = transitionScope,
            animatedContentScope = animatedContentScope,
            popularMovies = uiState.popularMovies,
            topRatedMovies = uiState.topRatedMovies,
            upcomingMovies = uiState.upcomingMovies,
            moviesWatchlist = uiState.moviesWatchlist,
            trendingMovies = uiState.trendingMovies,
            onMovieSelected = onMovieSelected,
            onTopRatedMoviesThresholdReached = onTopRatedMoviesThresholdReached,
            onUpcomingMoviesThresholdReached = onUpcomingMoviesThresholdReached,
            onPopularMoviesThresholdReached = onPopularMoviesThresholdReached,
            onTrendingMoviesThresholdReached = onTrendingMoviesThresholdReached
        )
        is MoviesViewModel.MoviesUiState.Loading -> Content(
            transitionScope = transitionScope,
            animatedContentScope = animatedContentScope,
            loading = true
        )
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
    popularMovies: List<PosterItem> = emptyList(),
    topRatedMovies: List<PosterItem> = emptyList(),
    upcomingMovies: List<PosterItem> = emptyList(),
    moviesWatchlist: List<HighlightedItem> = emptyList(),
    trendingMovies: List<PosterItem> = emptyList(),
    onMovieSelected: (id: Int, origin: String) -> Unit = {_,_->},
    loading: Boolean = false,
    onTopRatedMoviesThresholdReached: () -> Unit = {},
    onUpcomingMoviesThresholdReached: () -> Unit = {},
    onPopularMoviesThresholdReached: () -> Unit = {},
    onTrendingMoviesThresholdReached: () -> Unit = {}
){
    Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
        Spacer(
            Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp), MaterialTheme.colorScheme.surface ), startY = 200f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp, top = 40.dp)
        ) {
            if(moviesWatchlist.isNotEmpty()) {
                val watchlistTitle = stringResource(R.string.watchlist_title)
                SectionTitle(
                    watchlistTitle,
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading = loading
                )

                HighlightedSection(
                    highlightedList = moviesWatchlist,
                    onItemSelected = {onMovieSelected(it, watchlistTitle)},
                    loading = loading,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.trending_title),
                trendingMovies,
                onMovieSelected,
                onTrendingMoviesThresholdReached
            )

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.top_rated_title),
                topRatedMovies,
                onMovieSelected,
                onTopRatedMoviesThresholdReached
            )

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.popular_title),
                popularMovies,
                onMovieSelected,
                onPopularMoviesThresholdReached
            )

            PosterSection(
                transitionScope,
                animatedContentScope,
                loading,
                stringResource(R.string.upcoming_title),
                upcomingMovies,
                onMovieSelected,
                onUpcomingMoviesThresholdReached
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
    movies: List<PosterItem>,
    onMovieSelected: (id: Int, origin: String) -> Unit,
    onMoviesThresholdReached: () -> Unit
) {
    SectionTitle(
        sectionTitle,
        Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        loading = loading
    )

    ListSection(
        posterList = movies,
        onScrollThresholdReached = onMoviesThresholdReached,
        modifier = Modifier.padding(top = 8.dp),
        loading = loading
    ) { posterItem ->
        PosterItemView(
            Modifier
                .padding(horizontal = 8.dp)
                .width(130.dp)
                .aspectRatio(0.67f),
            posterItem.rating,
            { onMovieSelected(posterItem.id, sectionTitle) }
        ) { posterShape ->
            with(transitionScope) {
                PosterImage(
                    posterItem.posterUrl,
                    Modifier
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



