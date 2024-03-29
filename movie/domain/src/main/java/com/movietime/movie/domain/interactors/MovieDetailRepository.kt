package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    val movieDetail: Flow<MovieDetail>
    val movieVideos: Flow<List<Video>>
    val recommendedMovies: Flow<List<MoviePreview>>

    suspend fun fetchMovieDetail()
    suspend fun fetchMovieVideos()

    suspend fun refreshRecommendations()
    suspend fun fetchMoreRecommendations()
}

interface MovieDetailRepositoryFactory{
    fun create(movieId: Int): MovieDetailRepository
}