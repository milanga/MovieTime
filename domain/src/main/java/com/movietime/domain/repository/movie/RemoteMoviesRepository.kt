package com.movietime.domain.repository.movie

import com.movietime.domain.interactors.movie.MoviesRepository
import com.movietime.domain.model.MoviePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RemoteMoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesDataSource,
): MoviesRepository {
    private var popularMoviesPage = 1
    private val _popularMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val popularMovies: Flow<List<MoviePreview>> = _popularMovies

    private var topRatedMoviesPage = 1
    private val _topRatedMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val topRatedMovies: Flow<List<MoviePreview>> = _topRatedMovies

    private var upcomingMoviesPage = 1
    private val _upcomingMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val upcomingMovies: Flow<List<MoviePreview>> = _upcomingMovies

    private var trendingMoviesPage = 1
    private val _trendingMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val trendingMovies: Flow<List<MoviePreview>> = _trendingMovies

    override suspend fun refreshPopularMovies() {
        popularMoviesPage = 1
        _popularMovies.emit(remoteDataSource.getPopularMovies(popularMoviesPage))
    }
    override suspend fun fetchMorePopularMovies() {
        popularMoviesPage++
        _popularMovies.emit(_popularMovies.value.plus(remoteDataSource.getPopularMovies(popularMoviesPage)))
    }

    override suspend fun refreshTopRatedMovies() {
        topRatedMoviesPage = 1
        _topRatedMovies.emit(remoteDataSource.getTopRatedMovies(topRatedMoviesPage))
    }
    override suspend fun fetchMoreTopRatedMovies() {
        topRatedMoviesPage++
        _topRatedMovies.emit(_topRatedMovies.value.plus(remoteDataSource.getTopRatedMovies(topRatedMoviesPage)))
    }

    override suspend fun refreshUpcomingMovies() {
        upcomingMoviesPage = 1
        _upcomingMovies.emit(remoteDataSource.getUpcomingMovies(upcomingMoviesPage))
    }
    override suspend fun fetchMoreUpcomingMovies() {
        upcomingMoviesPage++
        _upcomingMovies.emit(_upcomingMovies.value.plus(remoteDataSource.getUpcomingMovies(upcomingMoviesPage)))
    }

    override suspend fun refreshTrendingMovies() {
        trendingMoviesPage = 1
        _trendingMovies.emit(remoteDataSource.getTrendingMovies(trendingMoviesPage))
    }
    override suspend fun fetchMoreTrendingMovies() {
        trendingMoviesPage++
        _trendingMovies.emit(_trendingMovies.value.plus(remoteDataSource.getTrendingMovies(trendingMoviesPage)))
    }
}