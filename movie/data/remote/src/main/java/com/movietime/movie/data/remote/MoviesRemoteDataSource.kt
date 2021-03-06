package com.movietime.movie.data.remote

import com.movietime.movie.data.remote.mappers.MoviePreviewMapper
import com.movietime.movie.data.remote.model.RemoteMoviesResponse
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.repository.MoviesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService,
    private val mapper: MoviePreviewMapper
) : MoviesDataSource {
    override fun getPopularMovies(page: Int): Flow<List<MoviePreview>> = flow {
        emit(moviesService.popularMovies(page))
    }.flowOn(Dispatchers.IO)
        .map { remoteMovieResponse: RemoteMoviesResponse ->
            remoteMovieResponse.movies.map { remoteMoviePreview ->
                mapper.map(
                    remoteMoviePreview
                )
            }
        }
        .flowOn(Dispatchers.Default)

    override fun getTopRatedMovies(page: Int): Flow<List<MoviePreview>> = flow {
        emit(moviesService.topRatedMovies(page))
    }.flowOn(Dispatchers.IO)
        .map { remoteMovieResponse: RemoteMoviesResponse ->
            remoteMovieResponse.movies.map { remoteMoviePreview ->
                mapper.map(
                    remoteMoviePreview
                )
            }
        }
        .flowOn(Dispatchers.Default)

    override fun getUpcomingMovies(page: Int): Flow<List<MoviePreview>> = flow {
        emit(moviesService.upcomingMovies(page))
    }.flowOn(Dispatchers.IO)
        .map { remoteMovieResponse: RemoteMoviesResponse ->
            remoteMovieResponse.movies.map { remoteMoviePreview ->
                mapper.map(
                    remoteMoviePreview
                )
            }
        }
        .flowOn(Dispatchers.Default)
}