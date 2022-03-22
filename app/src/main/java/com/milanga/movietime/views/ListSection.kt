package com.milanga.movietime.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.placeholder
import com.milanga.movietime.movies.MoviePreview
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlin.math.ceil


@Composable
fun ListSection(
    moviesList: List<MoviePreview> = emptyList(),
    onMovieSelected: (id: Int) -> Unit = {},
    modifier: Modifier = Modifier,
    onScrollThresholdReached: ()->Unit = {},
    loading: Boolean = false
) {
    if(loading){
        LoadingList()
    } else {
        MovieList(moviesList, onMovieSelected, modifier, onScrollThresholdReached)
    }
}

@Composable
fun LoadingList(){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val posterWidth = 130.0

    //make list not scrollable
    val state = rememberLazyListState()
    LaunchedEffect(key1 = state){
        launch {
            state.scroll(scrollPriority = MutatePriority.PreventUserInput) { awaitCancellation()}
        }
    }

    LazyRow(
        modifier = Modifier
            .padding(start = 8.dp),
        state = state
    ) {
        for (i in 0..ceil(screenWidth/posterWidth).toInt()){
            item {
                PosterItem(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(130.dp)
                        .aspectRatio(0.67f),
                    loading = true
                )
            }
        }
    }
}

@Composable
fun MovieList(
    movieList: List<MoviePreview>,
    onMovieSelected: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    onScrollThresholdReached: ()->Unit = {},
    threshold: Int = 5
){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        itemsIndexed(items = movieList) { index, movie ->
            if (movieList.size - index < threshold){
                onScrollThresholdReached.invoke()
            }
            PosterItem(
                Modifier
                    .padding(8.dp)
                    .width(130.dp)
                    .aspectRatio(0.67f)
                    .clickable { onMovieSelected(movie.id) },
                movie.getPosterUrl(),
                movie.getRating().toString()
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PosterItem(
    modifier: Modifier = Modifier,
    posterUrl: String = "",
    rating: String = "",
    loading: Boolean = false
) {
    Surface(
        modifier = modifier
            .placeholder(
                loading,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(18.dp)
            ),
        tonalElevation = 3.dp,
        shape = RoundedCornerShape(18.dp)
    ) {
        Box(modifier= Modifier.fillMaxSize()) {
            Image(
                painter = rememberImagePainter(
                    data = posterUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )

            Surface(
                tonalElevation = 4.dp,
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                modifier = Modifier
                    .padding(4.dp)
                    .width(36.dp)
                    .height(36.dp)
                    .align(Alignment.BottomEnd)

            ) {
                Box {
                    Text(
                        text = rating,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PosterItemPreview() {
    PosterItem(
        modifier = Modifier
            .padding(16.dp)
            .width(120.dp)
            .height(180.dp),
        posterUrl = "",
        rating = "5.7"
    )
}
