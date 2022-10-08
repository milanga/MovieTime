package com.movietime.movie.domain.repository

import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.model.Video

interface MovieDetailDataSource {
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getMovieVideos(movieId: Int): List<Video>
    suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MoviePreview>
}