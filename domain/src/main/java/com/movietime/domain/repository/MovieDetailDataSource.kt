package com.movietime.domain.repository

import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video

interface MovieDetailDataSource {
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getMovieVideos(movieId: Int): List<Video>
    suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MoviePreview>
}