package com.movietime.movie.domain.repository

import com.movietime.movie.domain.interactors.MovieDetailRepository
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val remoteDetailDataSource: MovieDetailDataSource,
): MovieDetailRepository {
    override fun getMovieDetail(movieId: Int) = remoteDetailDataSource.getMovieDetail(movieId)
    override fun getMovieVideos(movieId: Int) = remoteDetailDataSource.getMovieVideos(movieId)
    override fun getMovieRecommendations(movieId: Int, page: Int) = remoteDetailDataSource.getMovieRecommendations(movieId, page)
}