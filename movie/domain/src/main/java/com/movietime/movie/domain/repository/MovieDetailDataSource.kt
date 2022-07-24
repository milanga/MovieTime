package com.movietime.movie.domain.repository

import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieDetailDataSource {
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
    fun getMovieVideos(movieId: Int): Flow<List<Video>>
    fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<MoviePreview>>
}