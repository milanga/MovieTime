package com.movietime.core.views.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.movietime.core.views.poster.PosterImage
import com.movietime.core.views.poster.PosterItemView

@Composable
fun DetailSection(
    posterUrl: String,
    detailRows: List<DetailRowData>,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    posterItemView: @Composable RowScope.() -> Unit = {}
) {
    Row(modifier = modifier) {
        posterItemView()
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

