package com.movietime.movie.model.repository

import com.movietime.movie.model.model.MovieDetail
import com.movietime.movie.model.model.MoviePreview
import com.movietime.movie.model.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieDetailDataSource {
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
    fun getMovieVideos(movieId: Int): Flow<List<Video>>
    fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<MoviePreview>>
}