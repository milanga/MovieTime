package com.movietime.domain.repository.movie

import com.movietime.domain.model.MediaIds

interface MovieIdsDataSource {
    suspend fun getMovieIds(id: String): MediaIds
}