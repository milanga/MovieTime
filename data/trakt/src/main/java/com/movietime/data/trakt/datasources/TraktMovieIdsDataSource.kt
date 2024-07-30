package com.movietime.data.trakt.datasources

import com.movietime.data.trakt.mappers.MediaIdsMapper
import com.movietime.data.trakt.service.MovieService
import com.movietime.domain.model.MediaIds
import com.movietime.domain.repository.movie.MovieIdsDataSource
import javax.inject.Inject

class TraktMovieIdsDataSource @Inject constructor(
    private val movieService: MovieService,
    private val movieIdsMapper: MediaIdsMapper
): MovieIdsDataSource {
    override suspend fun getMovieIds(id: String): MediaIds {
        return movieIdsMapper.map(movieService.getMovieDetail(id).ids)
    }
}