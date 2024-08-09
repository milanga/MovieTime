package com.movietime.core.views.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.movietime.core.views.poster.PosterItemView

@Composable
fun DetailSection(
    posterUrl: String,
    detailRows: List<DetailRowData>,
    modifier: Modifier = Modifier,
    loading: Boolean = false
) {
    Row(modifier = modifier) {
        PosterItemView(
            posterUrl = posterUrl,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .aspectRatio(0.67f),
            loading = loading
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            detailRows.forEachIndexed { index, it ->
                val topPadding = if(index == 0) 8.dp else 0.dp
                DetailRow(
                    detailRowData = it,
                    modifier = Modifier.padding(bottom = 8.dp, top = topPadding)
                )
            }
        }
    }
}

