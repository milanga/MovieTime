package com.movietime.movietime.movies.interactors

import com.movietime.movietime.movies.data.MoviesRepository
import com.movietime.movietime.movies.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(page: Int = 1): Flow<List<MoviePreview>> =
        moviesRepository.getUpcomingMovies(page)
}