package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(page: Int = 1): Flow<List<MoviePreview>> =
        moviesRepository.getTopRatedMovies(page)
}