package com.movietime.domain.interactors.movie

import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MovieIds
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    val movieDetail: Flow<MovieDetail>
    val movieIds: Flow<MovieIds>
    val movieVideos: Flow<List<Video>>
    val recommendedMovies: Flow<List<MoviePreview>>

    suspend fun fetchMovieDetail()
    suspend fun fetchMovieIds(imdbId: String)
    suspend fun fetchMovieVideos()

    suspend fun refreshRecommendations()
    suspend fun fetchMoreRecommendations()
}

interface MovieDetailRepositoryFactory{
    fun create(movieId: Int): MovieDetailRepository
}