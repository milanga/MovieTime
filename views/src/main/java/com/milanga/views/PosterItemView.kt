package com.milanga.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.placeholder

@OptIn(
    ExperimentalCoilApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PosterItemView(
    modifier: Modifier = Modifier,
    posterUrl: String = "",
    rating: String = "",
    onClick: (() -> Unit)? = null,
    loading: Boolean = false
) {
    val posterShape = RoundedCornerShape(18.dp)
    val clickModifier = if (onClick != null) {
        Modifier
            .clip(posterShape)
            .clickable(onClick = onClick)
    } else {
        Modifier
    }
    val finalModifier = modifier
        .placeholder(
            loading,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = posterShape
        )
        .background(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            shape = posterShape
        )
        .then(clickModifier)

    Box(modifier = finalModifier) {
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
                .clip(posterShape)
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

@Preview
@Composable
private fun PosterItemPreview() {
    PosterItemView(
        modifier = Modifier
            .padding(16.dp)
            .width(120.dp)
            .height(180.dp),
        posterUrl = "",
        rating = "5.7"
    )
}