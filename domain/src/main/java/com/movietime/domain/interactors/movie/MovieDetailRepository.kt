package com.movietime.domain.interactors.movie

import com.movietime.domain.model.MediaIds
import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getMovieDetail(tmdbMovieId: Int): Flow<MovieDetail>
    fun getMovieIds(imdbMovieId: String): Flow<MediaIds>
    fun getMovieVideos(tmdbMovieId: Int): Flow<List<Video>>
    fun getRecommendations(tmdbMovieId: Int, page: Int): Flow<List<MoviePreview>>
}