package com.milanga.movietime.movies.detail

import com.milanga.movietime.movies.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MovieDetailDataSource {
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
    fun getMovieVideos(movieId: Int): Flow<List<Video>>
    fun getMovieRecommendations(movieId: Int, page: Int): Flow<MoviesResponse>
}