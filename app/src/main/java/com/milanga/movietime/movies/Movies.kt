package com.milanga.movietime.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.milanga.compose.black60Opacity
import com.milanga.movietime.R
import com.milanga.movietime.core.UIContentState
import com.milanga.movietime.views.ListSection
import com.milanga.movietime.views.PosterItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.milanga.movietime.views.SectionTitle
import kotlin.math.absoluteValue

@Composable
fun Movies(viewModel: MoviesViewModel = hiltViewModel(), onMovieSelected: (id: Int)->Unit) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Transparent
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiStateStateFlowLifecycleAware = remember(viewModel.uiState, lifecycleOwner) {
        viewModel.uiState.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val uiState = uiStateStateFlowLifecycleAware.collectAsState(MoviesViewModel.MoviesUiState.Content()).value

    when(uiState){
        is MoviesViewModel.MoviesUiState.Error -> ErrorScreen(uiState)
        is MoviesViewModel.MoviesUiState.Content -> Content(uiState, onMovieSelected, {viewModel.onTopRatedMoviesThreshold()}, {viewModel.onUpcomingMoviesThreshold()}, {viewModel.onPopularMoviesThreshold()})
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
            .background(MaterialTheme.colorScheme.surface)
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
    uiContentState: UIContentState<List<MoviePreview>>,
    onMovieSelected: (id: Int) -> Unit,
    onTopRatedMoviesThresholdReached: () -> Unit
) {
    when (uiContentState) {
        is UIContentState.Loading -> ListSection(loading = true)
        is UIContentState.ContentState -> ListSection(
            uiContentState.content,
            onMovieSelected,
            onScrollThresholdReached = onTopRatedMoviesThresholdReached
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
            .aspectRatio(1.5f)
            .placeholder(
                loading,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
    ) { page ->
        if (popularMovies.size - page < threshold){
            onScrollThresholdReached.invoke()
        }
        BackdropItem(
            backdropUrl = popularMovies[page].getBackdropUrl(),
            posterUrl = popularMovies[page].getPosterUrl(),
            modifier = Modifier
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.95f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
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
            rating = popularMovies[page].getRating(),
            onClick = { onMovieSelected(popularMovies[page].id) }
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun BackdropItem(
    modifier: Modifier = Modifier,
    backdropUrl: String,
    posterUrl: String,
    title: String,
    rating: Float,
    overview: String,
    onClick: ()->Unit = {}
) {
    Surface(
        onClick = onClick,
        tonalElevation = 3.dp,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = rememberImagePainter(
                    data = backdropUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
            )
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = black60Opacity)
                    .padding(horizontal = 16.dp)
            ) {
                PosterItem(
                    Modifier
                        .padding(vertical = 38.dp)
                        .fillMaxHeight()
                        .aspectRatio(0.66f, true),
                    posterUrl,
                    rating.toString()

                )
                Column(
                    modifier = Modifier.padding(
                        top = 32.dp, start = 16.dp, end = 16.dp
                    )
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        color = Color.White
                    )
                    Text(
                        text = overview,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun BackdropItemPreview() {
    BackdropItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(182.dp),
        backdropUrl = "",
        posterUrl = "",
        title = "Dune",
        overview = "Paul Altreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the result",
        rating = 8f
    )
}