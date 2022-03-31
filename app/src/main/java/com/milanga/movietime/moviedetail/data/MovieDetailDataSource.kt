package com.milanga.movietime.moviedetail.data

import com.milanga.movietime.moviedetail.domain.MovieDetail
import com.milanga.movietime.moviedetail.domain.Video
import com.milanga.movietime.movies.domain.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MovieDetailDataSource {
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
    fun getMovieVideos(movieId: Int): Flow<List<Video>>
    fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<MoviePreview>>
}