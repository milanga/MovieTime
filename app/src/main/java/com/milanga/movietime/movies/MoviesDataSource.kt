package com.milanga.movietime.movies

import com.milanga.movietime.movies.detail.MovieDetail
import com.milanga.movietime.movies.detail.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface MoviesDataSource {
    fun getPopularMovies(page: Int): Flow<MoviesResponse>
    fun getTopRatedMovies(page: Int): Flow<MoviesResponse>
    fun getUpcomingMovies(page: Int): Flow<MoviesResponse>
}