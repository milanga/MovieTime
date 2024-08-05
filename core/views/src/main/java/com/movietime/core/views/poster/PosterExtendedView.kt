package com.movietime.core.views.poster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PosterExtendedView(
    posterUrl: String,
    rating: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit) = {},
    loading: Boolean = false,
) {
    val posterShape = RoundedCornerShape(18.dp)
    Column(modifier = getCompleteModifier(posterShape, modifier, onClick, loading)) {
        PosterImage(
            posterUrl,
            Modifier
                .fillMaxWidth()
                .aspectRatio(0.67f)
                .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
        )
        ExtendedTitle(title, rating)
    }
}

@Composable
private fun ExtendedTitle(
    title: String,
    rating: String
) {
    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
        .fillMaxWidth()
        .defaultMinSize(minHeight = 44.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 3,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .padding(bottom = 2.dp)
        )
        if (rating.isNotEmpty()) {
            Rate(
                rating,
                Modifier
                    .padding(4.dp)
                    .width(36.dp)
                    .height(36.dp)
                    .align(Alignment.Bottom)
            )
        }
    }
}

@Preview
@Composable
private fun previewPosterExtendedView() {
    PosterExtendedView(
        posterUrl = "https://image.tmdb.org/t/p/w500/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg",
        rating = "8.6",
        title = "The Shawshank Redemption",
        modifier = Modifier
                .width(200.dp)
                .wrapContentHeight()
    )
}