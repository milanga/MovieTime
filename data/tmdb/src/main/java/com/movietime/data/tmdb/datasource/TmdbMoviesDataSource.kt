package com.movietime.data.tmdb.datasource

import com.movietime.data.tmdb.service.MoviesService
import com.movietime.data.tmdb.mappers.MoviePreviewMapper
import com.movietime.data.tmdb.model.TmdbMoviesResponse
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.repository.MoviesDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class TmdbMoviesDataSource @Inject constructor(
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

    private fun getListOfMovies(tmdbMoviesResponse: TmdbMoviesResponse): List<MoviePreview> =
        tmdbMoviesResponse.movies.map { remoteMoviePreview ->
            mapper.map(
                remoteMoviePreview
            )
        }

}