package com.movietime.movie.home.interactors

import com.movietime.movie.home.data.MoviesRepository
import com.movietime.movie.home.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(page: Int = 1): Flow<List<MoviePreview>> =
        moviesRepository.getTopRatedMovies(page)
}