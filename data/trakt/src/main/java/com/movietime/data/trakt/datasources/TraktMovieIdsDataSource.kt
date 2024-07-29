package com.movietime.data.trakt.datasources

import com.movietime.data.trakt.mappers.MovieIdsMapper
import com.movietime.data.trakt.service.MovieService
import com.movietime.domain.model.MovieIds
import com.movietime.domain.repository.movie.MovieIdsDataSource
import javax.inject.Inject

class TraktMovieIdsDataSource @Inject constructor(
    private val movieService: MovieService,
    private val movieIdsMapper: MovieIdsMapper
): MovieIdsDataSource {
    override suspend fun getMovieIds(id: String): MovieIds {
        return movieIdsMapper.map(movieService.getMovieDetail(id).ids)
    }
}