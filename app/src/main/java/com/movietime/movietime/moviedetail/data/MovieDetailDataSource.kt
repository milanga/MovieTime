package com.movietime.movietime.moviedetail.data

import com.movietime.movietime.moviedetail.domain.MovieDetail
import com.movietime.movietime.moviedetail.domain.Video
import com.movietime.movie.home.domain.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MovieDetailDataSource {
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
    fun getMovieVideos(movieId: Int): Flow<List<Video>>
    fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<MoviePreview>>
}