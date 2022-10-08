package com.movietime.movie.data.remote

import com.movietime.movie.data.remote.mappers.MoviePreviewMapper
import com.movietime.movie.data.remote.model.RemoteMoviesResponse
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.repository.MoviesDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class MoviesRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService,
    private val mapper: MoviePreviewMapper,
    @Named("io_dispatcher") private val coroutineContext: CoroutineContext
) : MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): List<MoviePreview> = withContext(coroutineContext) {
        getListOfMovies(moviesService.popularMovies(page))
    }

    override suspend fun getTopRatedMovies(page: Int): List<MoviePreview> = withContext(coroutineContext) {
        getListOfMovies(moviesService.topRatedMovies(page))
    }


    override suspend fun getUpcomingMovies(page: Int): List<MoviePreview> = withContext(coroutineContext) {
        getListOfMovies(moviesService.upcomingMovies(page))
    }

    private fun getListOfMovies(remoteMoviesResponse: RemoteMoviesResponse): List<MoviePreview> =
        remoteMoviesResponse.movies.map { remoteMoviePreview ->
            mapper.map(
                remoteMoviePreview
            )
        }

}