package com.example.movietime.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.compose.black60Opacity
import com.example.compose.black85Opacity
import com.example.compose.yellow
import com.example.movietime.core.UIContentState
import com.example.movietime.movies.MoviePreview
import kotlin.math.ceil


@Composable
fun ListSection(
    moviesContent: UIContentState<List<MoviePreview>>,
    title: String,
    onMovieSelected: (id: Int) -> Unit,
    onScrollThresholdReached: ()->Unit = {}
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        when(moviesContent){
            is UIContentState.Loading -> LoadingList()
            is UIContentState.ContentState -> MovieList(moviesContent.content, onMovieSelected, onScrollThresholdReached)
        }

    }
}

@Composable
fun LoadingList(){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val posterWidth = 120.0
    Row(modifier = Modifier.padding(start = 8.dp)) {
        for (i in 0..ceil(screenWidth/posterWidth).toInt()){
            Surface(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .width(120.dp)
                    .height(180.dp),
                tonalElevation = 3.dp,
                shadowElevation = 3.dp,
                shape = RoundedCornerShape(5.dp)
            ) { }
        }
    }
}

@Composable
fun MovieList(
    movieList: List<MoviePreview>,
    onMovieSelected: (id: Int) -> Unit,
    onScrollThresholdReached: ()->Unit = {},
    threshold: Int = 5
){
    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
        itemsIndexed(items = movieList) { index, movie ->
            if (movieList.size - index < threshold){
                onScrollThresholdReached.invoke()
            }
            PosterItem(
                Modifier
                    .padding(8.dp)
                    .width(120.dp)
                    .height(180.dp)
                    .clickable { onMovieSelected(movie.id) },
                movie.getPosterUrl(),
                movie.rating.toString()
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PosterItem(
    modifier: Modifier = Modifier,
    posterUrl: String = "",
    rating: String = "5.5"
) {
    Surface(
        modifier = modifier,
        tonalElevation = 3.dp,
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Box {
            Image(
                painter = rememberImagePainter(
                    data = posterUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Row(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                black60Opacity,
                                black85Opacity
                            )
                        )
                    )
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 5.dp, bottom = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = yellow,
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp)
                        .align(
                            Alignment.CenterVertically
                        )
                )
                Text(
                    text = rating,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
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
        posterUrl = ""
    )
}
