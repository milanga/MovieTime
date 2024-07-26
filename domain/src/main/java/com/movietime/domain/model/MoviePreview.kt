package com.movietime.domain.model

import android.provider.MediaStore.Audio.Media
import java.util.*

data class MoviePreview(
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    override val id: Int,
    override val posterUrl: String,
    val backdropUrl: String,
    val rating: Float,
    val voteCount: Int,
    val popularity: Float,
    val overview: String,
    val genreIds: List<Int>,
    val releaseDate: Date?,
    val adult: Boolean,
    val video: Boolean,
): MediaPreview