package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.repository.MoviesRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val moviesRepositoryImpl: MoviesRepositoryImpl
) {
    operator fun invoke(page: Int = 1): Flow<List<MoviePreview>> =
        moviesRepositoryImpl.getUpcomingMovies(page)
}