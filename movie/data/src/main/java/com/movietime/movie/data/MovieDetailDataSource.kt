package com.movietime.movie.data

import com.movietime.movie.domain.detail.MovieDetail
import com.movietime.movie.domain.detail.Video
import kotlinx.coroutines.flow.Flow

interface MovieDetailDataSource {
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
    fun getMovieVideos(movieId: Int): Flow<List<Video>>
    fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<com.movietime.movie.domain.MoviePreview>>
}