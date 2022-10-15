package com.movietime.movie.domain.repository

import com.movietime.movie.domain.interactors.MoviesRepository
import com.movietime.movie.domain.model.MoviePreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RemoteMoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesDataSource,
): MoviesRepository {
    private val _popularMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val popularMovies: Flow<List<MoviePreview>> = _popularMovies

    private val _topRatedMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val topRatedMovies: Flow<List<MoviePreview>> = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val upcomingMovies: Flow<List<MoviePreview>> = _upcomingMovies
    override suspend fun fetchPopularMovies(page: Int) {
        _popularMovies.emit(_popularMovies.value.plus(remoteDataSource.getPopularMovies(page)))
    }
    override suspend fun fetchTopRatedMovies(page: Int) {
        _topRatedMovies.emit(_topRatedMovies.value.plus(remoteDataSource.getTopRatedMovies(page)))
    }
    override suspend fun fetchUpcomingMovies(page: Int) {
        _upcomingMovies.emit(_upcomingMovies.value.plus(remoteDataSource.getUpcomingMovies(page)))
    }
}