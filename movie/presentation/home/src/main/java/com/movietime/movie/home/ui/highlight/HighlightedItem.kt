package com.movietime.movie.home.ui.highlight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.movietime.movie.home.ui.black60Opacity
import com.movietime.views.PosterItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HighlightedItem(
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
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            HighlightBackdropImage(
                backdropUrl,
                Modifier
                    .matchParentSize()
            )

            HighlightContent(
                posterUrl,
                rating,
                title,
                overview,
                Modifier
                    .matchParentSize()
                    .background(color = black60Opacity)
                    .padding(start = 16.dp, end = 16.dp, top = 38.dp)
            )

        }
    }
}

@Composable
private fun HighlightBackdropImage(backdropUrl: String, modifier: Modifier){
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = backdropUrl)
                .apply(
                    block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }
                ).build()
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
private fun HighlightContent(
    posterUrl: String,
    rating: Float,
    title: String,
    overview: String,
    modifier: Modifier
){
    Row(
        modifier = modifier
    ) {
        PosterItemView(
            Modifier
                .padding(bottom = 38.dp)
                .fillMaxHeight()
                .aspectRatio(0.67f, true),
            posterUrl,
            rating.toString()
        )

        HighlightDescription(title, overview)
    }
}

@Composable
private fun HighlightDescription(
    title: String,
    overview: String
){
    Column(
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 2,
            color = Color.White
        )
        Text(
            text = overview,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
private fun HighlightItemPreview() {
    HighlightedItem(
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