package com.movietime.domain.repository.movie

import com.movietime.domain.model.MovieIds

interface MovieIdsDataSource {
    suspend fun getMovieIds(id: String): MovieIds
}