package com.milanga.movietime.movies.interactors

import com.milanga.movietime.movies.data.MoviesRepository
import com.milanga.movietime.movies.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(page: Int = 1): Flow<List<MoviePreview>> =
        moviesRepository.getPopularMovies(page)
}