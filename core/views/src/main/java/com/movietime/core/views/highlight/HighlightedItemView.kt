package com.movietime.core.views.highlight

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.movietime.core.views.poster.PosterImage
import com.movietime.movie.home.ui.black75Opacity
import com.movietime.core.views.poster.PosterItemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighlightedItemView(
    modifier: Modifier = Modifier,
    backdropUrl: String = "",
    posterUrl: String = "",
    rating: String = "",
    overview: String = "",
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        tonalElevation = 3.dp,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HighlightBackdropImage(
                backdropUrl,
                Modifier
                    .matchParentSize()
            )

            HighlightContent(
                posterUrl,
                rating,
                overview,
                Modifier
                    .matchParentSize()
                    .background(color = black75Opacity)
                    .padding(16.dp)
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
    rating: String,
    overview: String,
    modifier: Modifier
){
    Row(
        modifier = modifier
    ) {
        PosterItemView(
            Modifier
                .fillMaxHeight()
                .aspectRatio(0.67f, true),
            rating
        ) { posterShape ->
            PosterImage(
                posterUrl = posterUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(posterShape)
            )
        }

        Text(
            text = overview,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
private fun HighlightItemPreview() {
    HighlightedItemView(
        modifier = Modifier
            .fillMaxWidth()
            .height(182.dp),
        backdropUrl = "",
        posterUrl = "",
        rating = "8.0",
        overview = "Paul Altreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the result"
    )
}