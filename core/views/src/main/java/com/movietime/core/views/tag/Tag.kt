package com.movietime.core.views.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Tag(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .then(
                Modifier
                    .background(
                        MaterialTheme.colorScheme.onSurface,
                        RoundedCornerShape(6.dp)
                    )
                    .padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
            )
    )
}

@Preview
@Composable
fun TagPreview() {
    Tag("Comedy")
}