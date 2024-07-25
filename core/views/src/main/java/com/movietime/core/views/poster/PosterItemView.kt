package com.movietime.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.placeholder

@Composable
fun PosterItemView(
    modifier: Modifier = Modifier,
    posterUrl: String = "",
    rating: String = "",
    onClick: (() -> Unit)? = null,
    loading: Boolean = false
) {
    val posterShape = RoundedCornerShape(18.dp)

    Box(modifier = getCompleteModifier(posterShape, modifier, onClick, loading)) {
        PosterImage(
            posterUrl,
            Modifier
                .fillMaxSize()
                .clip(posterShape)
        )

        if(rating.isNotEmpty()) {
            Rate(
                rating,
                Modifier
                    .padding(4.dp)
                    .width(36.dp)
                    .height(36.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun getCompleteModifier(
    posterShape: RoundedCornerShape,
    modifier: Modifier,
    onClick: (() -> Unit)?,
    loading: Boolean
): Modifier{
    val clickModifier = if (onClick != null) {
        Modifier
            .clip(posterShape)
            .clickable(onClick = onClick)
    } else {
        Modifier
    }
    return modifier
        .placeholder(
            loading,
            MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
            shape = posterShape
        )
        .then(clickModifier)
}

@Composable
private fun Rate(
    rating: String,
    modifier: Modifier
){
    Surface(
        tonalElevation = 4.dp,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
        modifier = modifier

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

@Composable
private fun PosterImage(
    posterUrl: String,
    modifier: Modifier
){
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = posterUrl)
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

@Preview
@Composable
private fun PosterItemPreview() {
    PosterItemView(
        modifier = Modifier
            .padding(16.dp)
            .width(120.dp)
            .height(180.dp),
        posterUrl = "",
        rating = "5.7",
        onClick = {}
    )
}